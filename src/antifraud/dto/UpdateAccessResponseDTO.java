package antifraud.dto;

import antifraud.constant.UserAccountStatus;
import antifraud.entity.auth.UserDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAccessResponseDTO {
    private String status;

    public UpdateAccessResponseDTO(UserDetail userDetail) {
        this.status = String.format("User %s %s!", userDetail.getUsername(), getOperation(userDetail.getStatus()));
    }

    private String getOperation(UserAccountStatus status) {
        if (UserAccountStatus.LOCK.equals(status)) {
            return "locked";
        } else {
            return "unlocked";
        }
    }
}
