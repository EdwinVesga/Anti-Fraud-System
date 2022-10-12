package antifraud.dto;

import antifraud.validator.constraints.RegionCodeConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import java.time.LocalDateTime;

import static antifraud.constant.Regex.IP_REGEX;

@Data
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull
    @Positive
    private Long amount;

    @NotBlank
    @Pattern(regexp = IP_REGEX)
    private String ip;

    @NotBlank
    @CreditCardNumber
    private String number;

    @NotEmpty
    @RegionCodeConstraint
    private String region;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime date;
}
