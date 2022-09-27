package antifraud.service;

import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;

public interface TransactionService {

    TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionRequestDTO);
}
