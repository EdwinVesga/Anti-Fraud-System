package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccessRequestDTO {

    private String username;

    private String operation;
}
