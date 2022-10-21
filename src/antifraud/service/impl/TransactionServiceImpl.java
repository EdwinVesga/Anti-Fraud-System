package antifraud.service.impl;

import antifraud.constant.RegionCode;
import antifraud.constant.TransactionType;
import antifraud.dto.TransactionFeedbackDTO;
import antifraud.dto.TransactionHistoryDTO;
import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import antifraud.entity.api.Transaction;
import antifraud.exception.ResourceConflictException;
import antifraud.exception.ResourceNotFoundException;
import antifraud.exception.ResourceUnprocessableEntityException;
import antifraud.limit.LimitProvider;
import antifraud.mapper.TransactionMapper;
import antifraud.repository.TransactionRepository;
import antifraud.service.StolenCardService;
import antifraud.service.SuspiciousIpService;
import antifraud.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final SuspiciousIpService suspiciousIpService;

    private final StolenCardService stolenCardService;

    private final TransactionRepository transactionRepository;

    private final LimitProvider allowedLimitProvider;


    private final LimitProvider manualLimitProvider;

    private final ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(SuspiciousIpService suspiciousIpService,
                                  StolenCardService stolenCardService,
                                  TransactionRepository transactionRepository,
                                  @Qualifier("allowedProvider") LimitProvider allowedLimitProvider,
                                  @Qualifier("manualProvider") LimitProvider manualLimitProvider,
                                  ModelMapper modelMapper) {
        this.suspiciousIpService = suspiciousIpService;
        this.stolenCardService = stolenCardService;
        this.transactionRepository = transactionRepository;
        this.allowedLimitProvider = allowedLimitProvider;
        this.manualLimitProvider = manualLimitProvider;
        this.modelMapper = modelMapper;
    }

    private Long countCorrelatedTransactionsByRegion(LocalDateTime date, String cardNumber, RegionCode regionCode) {
        LocalDateTime lastHourTime = date.minusHours(1);

        return transactionRepository.countByNumberAfterTime(lastHourTime, date, cardNumber, regionCode.name());
    }

    private Long countCorrelatedTransactionByIp(LocalDateTime date, String cardNumber, String ip) {
        LocalDateTime lastHourTime = date.minusHours(1);
        return transactionRepository.countByIpAfterTime(lastHourTime, date, cardNumber, ip);
    }

    @Override
    public TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionRequestDTO) {

        List<String> rejectingList = new ArrayList<>();

        TransactionType result;
        RegionCode region = RegionCode.getRegionCode(transactionRequestDTO.getRegion());

        Long correlationsByIp = countCorrelatedTransactionByIp(transactionRequestDTO.getDate(), transactionRequestDTO.getNumber(), transactionRequestDTO.getIp());
        Long correlationsByRegion = countCorrelatedTransactionsByRegion(transactionRequestDTO.getDate(), transactionRequestDTO.getNumber(), region);

        if (transactionRequestDTO.getAmount() > manualLimitProvider.getLimit()) {
            rejectingList.add("amount");
        }

        if (suspiciousIpService.isSuspiciousIp(transactionRequestDTO.getIp())) {
            rejectingList.add("ip");
        }

        if (stolenCardService.isStolenCard(transactionRequestDTO.getNumber())) {
            rejectingList.add("card-number");
        }

        if (correlationsByIp > 2) {
            rejectingList.add("ip-correlation");
        }

        if (correlationsByRegion > 2) {
            rejectingList.add("region-correlation");
        }

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        if (rejectingList.isEmpty()) {

            if (correlationsByIp == 2) {
                rejectingList.add("ip-correlation");
            }

            if (correlationsByRegion == 2) {
                rejectingList.add("region-correlation");
            }

            if (transactionRequestDTO.getAmount() > allowedLimitProvider.getLimit()) {
                 rejectingList.add("amount");
            }

            if (!rejectingList.isEmpty()) {
                result = TransactionType.MANUAL_PROCESSING;
                rejectingList.sort(String::compareTo);
                transactionResponseDTO.setInfo(String.join(", ", rejectingList));
            } else {
                result = TransactionType.ALLOWED;
                transactionResponseDTO.setInfo("none");
            }
        } else {
            result = TransactionType.PROHIBITED;
            rejectingList.sort(String::compareTo);
            transactionResponseDTO.setInfo(String.join(", ", rejectingList));
        }
        transactionResponseDTO.setResult(result);
        Transaction transaction = TransactionMapper.fromDTO(transactionRequestDTO);
        transaction.setResult(result.toString());
        transactionRepository.save(transaction);

        return transactionResponseDTO;
    }

    @Override
    public TransactionHistoryDTO putTransactionFeedback(TransactionFeedbackDTO transactionFeedbackDTO) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionFeedbackDTO.getTransactionId());
        Transaction t = transactionOptional.orElseThrow(() -> new ResourceNotFoundException());

        if (t.getResult().equals(transactionFeedbackDTO.getFeedback())) {
            throw new ResourceUnprocessableEntityException();
        }

        if (t.getFeedback() == null || t.getFeedback().isEmpty()) {
            t.setFeedback(transactionFeedbackDTO.getFeedback());
            transactionRepository.save(t);
            manageLimit(t.getResult(), t.getFeedback(), t.getAmount());
        } else {
            throw new ResourceConflictException();
        }

        return modelMapper.map(t, TransactionHistoryDTO.class);
    }

    private void manageLimit(String result, String feedback, Long transactionAmount) {

        List<String> transactionTypes = List.of("ALLOWED", "MANUAL_PROCESSING", "PROHIBITED");
        int idxResult = transactionTypes.indexOf(result);
        int idxFeedback = transactionTypes.indexOf(feedback);
        if (transactionTypes.indexOf(result) > transactionTypes.indexOf(feedback)) {
            for (int i = idxFeedback; i < idxResult; i++) {
                increaseLimit(transactionTypes.get(i), transactionAmount);
            }
        } else {
            for (int i = idxResult; i < idxFeedback; i++) {
                decreaseLimit(transactionTypes.get(i), transactionAmount);
            }
        }
    }

    private void increaseLimit(String limit, Long transactionAmount) {
        if (limit == "ALLOWED") {
            allowedLimitProvider.increaseLimit(transactionAmount);
        } else if (limit == "MANUAL_PROCESSING") {
            manualLimitProvider.increaseLimit(transactionAmount);
        }
    }

    private void decreaseLimit(String limit, Long transactionAmount) {
        if (limit == "ALLOWED") {
            allowedLimitProvider.decreaseLimit(transactionAmount);
        } else if (limit == "MANUAL_PROCESSING") {
            manualLimitProvider.decreaseLimit(transactionAmount);
        }
    }

    @Override
    public List<TransactionHistoryDTO> getTransactionHistory() {
        return transactionRepository.findAll()
                .stream().map(t -> modelMapper.map(t, TransactionHistoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionHistoryDTO> getTransactionHistoryByNumber(String number) {
        List<Transaction> transactionList = transactionRepository.findAllByNumber(number);

        if (transactionList.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return transactionList
                .stream().map(t -> modelMapper.map(t, TransactionHistoryDTO.class))
                .collect(Collectors.toList());
    }
}
