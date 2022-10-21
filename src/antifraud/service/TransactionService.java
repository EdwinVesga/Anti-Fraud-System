package antifraud.service;

import antifraud.dto.TransactionFeedbackDTO;
import antifraud.dto.TransactionHistoryDTO;
import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;

import javax.validation.Valid;
import java.util.List;

public interface TransactionService {

    TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionRequestDTO);

    TransactionHistoryDTO putTransactionFeedback(TransactionFeedbackDTO transactionFeedbackDTO);

    List<TransactionHistoryDTO> getTransactionHistory();

    List<TransactionHistoryDTO> getTransactionHistoryByNumber(String number);
}
