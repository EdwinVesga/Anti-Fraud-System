package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class UpdateRoleRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String role;
}
