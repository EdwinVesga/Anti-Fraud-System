package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserDetailRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
