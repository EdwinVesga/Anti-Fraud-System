package antifraud.service.impl;

import antifraud.dto.DeleteSuspiciousIpDTO;
import antifraud.dto.SuspiciousIpDTO;
import antifraud.entity.declined.SuspiciousIp;
import antifraud.exception.ResourceConflictException;
import antifraud.exception.ResourceNotFoundException;
import antifraud.repository.SuspiciousIpRepository;
import antifraud.service.SuspiciousIpService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuspiciousIpServiceImpl implements SuspiciousIpService {

    private final SuspiciousIpRepository suspiciousIpRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public SuspiciousIpServiceImpl(SuspiciousIpRepository suspiciousIpRepository, ModelMapper modelMapper) {
        this.suspiciousIpRepository = suspiciousIpRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SuspiciousIpDTO addSuspiciousIp(SuspiciousIpDTO suspiciousIpDTO) {
        Optional<SuspiciousIp> result = suspiciousIpRepository.findByIp(suspiciousIpDTO.getIp());

        result.ifPresent(s -> {
            throw new ResourceConflictException();
        });

        SuspiciousIp suspiciousIp = suspiciousIpRepository.save(modelMapper.map(suspiciousIpDTO, SuspiciousIp.class));

        return modelMapper.map(suspiciousIp, SuspiciousIpDTO.class);
    }

    @Override
    public List<SuspiciousIpDTO> getSuspiciousIpList() {

        return suspiciousIpRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, SuspiciousIpDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DeleteSuspiciousIpDTO deleteSuspiciousIp(String ipAddress) {
        Optional<SuspiciousIp> result = suspiciousIpRepository.findByIp(ipAddress);

        SuspiciousIp suspiciousIp = result.orElseThrow(() -> new ResourceNotFoundException());
        suspiciousIpRepository.delete(suspiciousIp);

        return new DeleteSuspiciousIpDTO(ipAddress);
    }

    @Override
    public boolean isSuspiciousIp(String ipAddress) {
        return suspiciousIpRepository.findByIp(ipAddress).isPresent();
    }
}
