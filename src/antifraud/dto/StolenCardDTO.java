package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class StolenCardDTO {

    private Long id;

    @NotBlank
    @CreditCardNumber
    private String number;
}
