package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static antifraud.constant.Regex.IP_REGEX;

@Data
@NoArgsConstructor
public class SuspiciousIpDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = IP_REGEX)
    private String ip;
}
