package antifraud.service.impl;

import antifraud.constant.TransactionResult;
import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import antifraud.service.StolenCardService;
import antifraud.service.SuspiciousIpService;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final SuspiciousIpService suspiciousIpService;

    private final StolenCardService stolenCardService;

    @Autowired
    public TransactionServiceImpl(SuspiciousIpService suspiciousIpService,
                                  StolenCardService stolenCardService) {
        this.suspiciousIpService = suspiciousIpService;
        this.stolenCardService = stolenCardService;
    }

    @Override
    public TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionRequestDTO) {

        List<String> rejectingList = new ArrayList<>();

        if (transactionRequestDTO.getAmount() > 1500) {
            rejectingList.add("amount");
        }

        if (suspiciousIpService.isSuspiciousIp(transactionRequestDTO.getIp())) {
            rejectingList.add("ip");
        }

        if (stolenCardService.isStolenCard(transactionRequestDTO.getNumber())) {
            rejectingList.add("card-number");
        }

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        if (rejectingList.isEmpty()) {
            if (transactionRequestDTO.getAmount() > 200) {
                transactionResponseDTO.setResult(TransactionResult.MANUAL_PROCESSING);
                transactionResponseDTO.setInfo("amount");
            } else {
                transactionResponseDTO.setResult(TransactionResult.ALLOWED);
                transactionResponseDTO.setInfo("none");
            }
        } else {
            rejectingList.sort(String::compareTo);
            transactionResponseDTO.setResult(TransactionResult.PROHIBITED);
            transactionResponseDTO.setInfo(String.join(", ", rejectingList));
        }

        return transactionResponseDTO;
    }

}
