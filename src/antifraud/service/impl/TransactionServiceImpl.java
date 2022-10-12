package antifraud.service.impl;

import antifraud.constant.RegionCode;
import antifraud.constant.TransactionResult;
import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import antifraud.entity.api.Transaction;
import antifraud.repository.TransactionRepository;
import antifraud.service.StolenCardService;
import antifraud.service.SuspiciousIpService;
import antifraud.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final SuspiciousIpService suspiciousIpService;

    private final StolenCardService stolenCardService;

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(SuspiciousIpService suspiciousIpService,
                                  StolenCardService stolenCardService,
                                  TransactionRepository transactionRepository,
                                  ModelMapper modelMapper) {
        this.suspiciousIpService = suspiciousIpService;
        this.stolenCardService = stolenCardService;
        this.transactionRepository = transactionRepository;
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

        RegionCode region = RegionCode.getRegionCode(transactionRequestDTO.getRegion());

        Long correlationsByIp = countCorrelatedTransactionByIp(transactionRequestDTO.getDate(), transactionRequestDTO.getNumber(), transactionRequestDTO.getIp());
        Long correlationsByRegion = countCorrelatedTransactionsByRegion(transactionRequestDTO.getDate(), transactionRequestDTO.getNumber(), region);

        if (transactionRequestDTO.getAmount() > 1500) {
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

            if (transactionRequestDTO.getAmount() > 200) {
                 rejectingList.add("amount");
            }

            if (!rejectingList.isEmpty()) {
                rejectingList.sort(String::compareTo);
                transactionResponseDTO.setResult(TransactionResult.MANUAL_PROCESSING);
                transactionResponseDTO.setInfo(String.join(", ", rejectingList));
            } else {
                transactionResponseDTO.setResult(TransactionResult.ALLOWED);
                transactionResponseDTO.setInfo("none");
            }
        } else {
            rejectingList.sort(String::compareTo);
            transactionResponseDTO.setResult(TransactionResult.PROHIBITED);
            transactionResponseDTO.setInfo(String.join(", ", rejectingList));
        }

        transactionRepository.save(modelMapper.map(transactionRequestDTO, Transaction.class));

        return transactionResponseDTO;
    }
}
