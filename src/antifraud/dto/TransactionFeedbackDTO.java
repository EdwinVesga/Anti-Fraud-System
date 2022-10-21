package antifraud.dto;

import antifraud.validator.constraints.TransactionTypeConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class TransactionFeedbackDTO {

    @Positive
    @NotNull
    private Long transactionId;

    @NotBlank
    @TransactionTypeConstraint
    private String feedback;
}
