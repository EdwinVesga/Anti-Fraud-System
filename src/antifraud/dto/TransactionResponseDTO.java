package antifraud.dto;

import antifraud.constant.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponseDTO {

    private TransactionType result;

    private String info;
}
