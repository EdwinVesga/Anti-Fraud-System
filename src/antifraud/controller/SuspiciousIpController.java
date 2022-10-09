package antifraud.controller;

import antifraud.dto.DeleteSuspiciousIpDTO;
import antifraud.dto.SuspiciousIpDTO;
import antifraud.service.SuspiciousIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

import static antifraud.constant.Regex.IP_REGEX;

@RestController
@PreAuthorize("hasRole('SUPPORT')")
@Validated
@RequestMapping("/api/antifraud/suspicious-ip")
public class SuspiciousIpController {

    private final SuspiciousIpService suspiciousIpService;

    @Autowired
    public SuspiciousIpController(SuspiciousIpService suspiciousIpService) {
        this.suspiciousIpService = suspiciousIpService;
    }

    @GetMapping
    public ResponseEntity<List<SuspiciousIpDTO>> getSuspiciousIpList() {
        return new ResponseEntity<>(suspiciousIpService.getSuspiciousIpList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuspiciousIpDTO> addSuspiciousIp(@Valid @RequestBody SuspiciousIpDTO suspiciousIpDTO) {
        return new ResponseEntity<>(suspiciousIpService.addSuspiciousIp(suspiciousIpDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{ip}")
    public ResponseEntity<DeleteSuspiciousIpDTO> deleteSuspiciousIp(@PathVariable @Pattern(regexp = IP_REGEX) String ip) {
        return new ResponseEntity<>(suspiciousIpService.deleteSuspiciousIp(ip), HttpStatus.OK);
    }
}
