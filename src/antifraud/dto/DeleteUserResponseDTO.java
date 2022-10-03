package antifraud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteUserResponseDTO {

    private static final String DELETED_USER_MSG = "Deleted successfully!";
    private String username;
    private String status;

    public DeleteUserResponseDTO(String username) {
        this.username = username;
        this.status = DELETED_USER_MSG;
    }
}
