package antifraud.service;

import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;

import javax.validation.Valid;

public interface TransactionService {

    TransactionResponseDTO makeTransaction(TransactionRequestDTO transactionRequestDTO);
}
