package antifraud.service.impl;

import antifraud.dto.DeleteSuspiciousIpDTO;
import antifraud.dto.SuspiciousIpDTO;
import antifraud.service.SuspiciousIpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SuspiciousIpServiceImpl implements SuspiciousIpService {

    private final SuspiciousIpService suspiciousIpService;

    @Autowired
    public SuspiciousIpServiceImpl(SuspiciousIpService suspiciousIpService) {
        this.suspiciousIpService = suspiciousIpService;
    }

    @Override
    public SuspiciousIpDTO addSuspiciousIp(SuspiciousIpDTO suspiciousIp) {
        return null;
    }

    @Override
    public List<SuspiciousIpDTO> getSuspiciousIpList() {
        return null;
    }

    @Override
    public DeleteSuspiciousIpDTO deleteSuspiciousIp(String ipAddress) {
        return null;
    }
}
