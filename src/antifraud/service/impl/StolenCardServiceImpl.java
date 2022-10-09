package antifraud.service.impl;

import antifraud.dto.DeleteStolenCardDTO;
import antifraud.dto.StolenCardDTO;
import antifraud.entity.declined.StolenCard;
import antifraud.exception.ResourceConflictException;
import antifraud.exception.ResourceNotFoundException;
import antifraud.repository.StolenCardRepository;
import antifraud.service.StolenCardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StolenCardServiceImpl implements StolenCardService {

    private final StolenCardRepository stolenCardRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StolenCardServiceImpl(StolenCardRepository stolenCardRepository, ModelMapper modelMapper) {
        this.stolenCardRepository = stolenCardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StolenCardDTO addStolenCard(StolenCardDTO stolenCardDTO) {
        Optional<StolenCard> result = stolenCardRepository.findByCardNumber(stolenCardDTO.getNumber());

        result.ifPresent(s -> {
            throw new ResourceConflictException();
        });

        StolenCard stolenCard = stolenCardRepository.save(modelMapper.map(stolenCardDTO, StolenCard.class));

        return modelMapper.map(stolenCard, StolenCardDTO.class);
    }

    @Override
    public List<StolenCardDTO> getStolenCardList() {
        return stolenCardRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, StolenCardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DeleteStolenCardDTO deleteStolenCard(String cardNumber) {
        Optional<StolenCard> result = stolenCardRepository.findByCardNumber(cardNumber);

        StolenCard suspiciousIp = result.orElseThrow(() -> new ResourceNotFoundException());
        stolenCardRepository.delete(suspiciousIp);

        return new DeleteStolenCardDTO(cardNumber);
    }

    @Override
    public boolean isStolenCard(String cardNumber) {
        return stolenCardRepository.findByCardNumber(cardNumber).isPresent();
    }
}
