package antifraud.mapper;

import antifraud.dto.TransactionRequestDTO;
import antifraud.entity.api.Transaction;

public class TransactionMapper {

    public static final Transaction fromDTO(TransactionRequestDTO transactionRequestDTO) {
        Transaction result = new Transaction();
        result.setAmount(transactionRequestDTO.getAmount());
        result.setIp(transactionRequestDTO.getIp());
        result.setDate(transactionRequestDTO.getDate());
        result.setRegion(transactionRequestDTO.getRegion());
        result.setNumber(transactionRequestDTO.getNumber());
        result.setFeedback("");
        return result;
    }
}
