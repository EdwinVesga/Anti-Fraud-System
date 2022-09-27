package antifraud.service;

import antifraud.constant.TransactionResult;
import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionRequestDTO) {

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        transactionResponseDTO.setResult(processTransaction(transactionRequestDTO.getAmount()));

        return transactionResponseDTO;
    }


    private TransactionResult processTransaction(Long amount) {
        TransactionResult result;

        if (amount <= 200) {
            result = TransactionResult.ALLOWED;
        } else if (amount <= 1500) {
            result = TransactionResult.MANUAL_PROCESSING;
        } else {
            result = TransactionResult.PROHIBITED;
        }

        return result;
    }
}
