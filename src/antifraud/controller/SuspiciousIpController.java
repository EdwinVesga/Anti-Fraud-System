package antifraud.controller;

import antifraud.dto.DeleteSuspiciousIpDTO;
import antifraud.dto.SuspiciousIpDTO;
import antifraud.service.SuspiciousIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud/suspicious-ip")
public class SuspiciousIpController {

    private final SuspiciousIpService suspiciousIpService;

    @Autowired
    public SuspiciousIpController(SuspiciousIpService suspiciousIpService) {
        this.suspiciousIpService = suspiciousIpService;
    }

    @GetMapping
    public ResponseEntity<List<SuspiciousIpDTO>> getSuspiciousIpList(SuspiciousIpDTO suspiciousIpDTO) {
        return new ResponseEntity<>(suspiciousIpService.getSuspiciousIpList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuspiciousIpDTO> addSuspiciousIp(SuspiciousIpDTO suspiciousIpDTO) {
        return new ResponseEntity<>(suspiciousIpService.addSuspiciousIp(suspiciousIpDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteSuspiciousIpDTO> deleteSuspiciousIp(String ipAddress) {
        return new ResponseEntity<>(suspiciousIpService.deleteSuspiciousIp(ipAddress), HttpStatus.OK);
    }
}
