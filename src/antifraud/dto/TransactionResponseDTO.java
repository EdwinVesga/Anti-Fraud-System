package antifraud.dto;

import antifraud.constant.TransactionResult;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponseDTO {

    private TransactionResult result;
}
