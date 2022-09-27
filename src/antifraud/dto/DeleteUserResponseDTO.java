package antifraud.dto;

public class DeleteUserResponseDTO {

    private static final String DELETED_USER_MSG = "Deleted successfully!";
    private String username;
    private String status;

    public DeleteUserResponseDTO() {
    }

    public DeleteUserResponseDTO(String username) {
        this.username = username;
        this.status = DELETED_USER_MSG;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
