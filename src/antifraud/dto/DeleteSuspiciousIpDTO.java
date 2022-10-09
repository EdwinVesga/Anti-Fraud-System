package antifraud.dto;

import lombok.Getter;

@Getter
public class DeleteSuspiciousIpDTO {

    private static final String DELETE_SUSPICIOUS_IP_MSG = "IP %s successfully removed!";

    private String status;

    public DeleteSuspiciousIpDTO(String ipAddress) {
        this.status = String.format(DELETE_SUSPICIOUS_IP_MSG, ipAddress);
    }
}
