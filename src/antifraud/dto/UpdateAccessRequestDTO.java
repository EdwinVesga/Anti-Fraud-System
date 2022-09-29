package antifraud.dto;

import antifraud.constant.UserAccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccessRequestDTO {
    private String username;
    private String operation;
}
