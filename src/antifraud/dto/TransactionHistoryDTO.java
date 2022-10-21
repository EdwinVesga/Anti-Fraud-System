package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionHistoryDTO {
    private Long transactionId;

    private Long amount;

    private String number;

    private String ip;

    private String region;

    private LocalDateTime date;

    private String result;

    private String feedback;
}
