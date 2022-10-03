package antifraud.service;

import antifraud.dto.DeleteSuspiciousIpDTO;
import antifraud.dto.SuspiciousIpDTO;

import java.util.List;

public interface SuspiciousIpService {

    SuspiciousIpDTO addSuspiciousIp(SuspiciousIpDTO suspiciousIpDTO);

    List<SuspiciousIpDTO> getSuspiciousIpList();

    DeleteSuspiciousIpDTO deleteSuspiciousIp(String ipAddress);
}
