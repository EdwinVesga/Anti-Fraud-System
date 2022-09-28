package antifraud.dto;

import antifraud.entity.UserDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDetailResponseDTO {

    private Long id;

    private String name;

    private String username;

    private String role;

    public UserDetailResponseDTO(UserDetail user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.role = user.getRole().getName().getRoleName();
    }
}
