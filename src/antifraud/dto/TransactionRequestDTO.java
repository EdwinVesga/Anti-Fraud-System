package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;

import static antifraud.constant.Regex.IP_REGEX;

@Data
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull
    @Positive
    private Long amount;

    @Pattern(regexp = IP_REGEX, message = "invalid ip format")
    private String ip;

    @CreditCardNumber(message="invalid card number format")
    private String number;
}
