package antifraud.service.impl;

import antifraud.dto.DeleteStolenCardDTO;
import antifraud.dto.StolenCardDTO;
import antifraud.repository.StolenCardRepository;
import antifraud.service.StolenCardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StolenCardServiceImpl implements StolenCardService {

    private final StolenCardRepository stolenCardRepository;

    @Autowired
    public StolenCardServiceImpl(StolenCardRepository stolenCardRepository) {
        this.stolenCardRepository = stolenCardRepository;
    }

    @Override
    public StolenCardDTO addStolenCard(StolenCardDTO stolenCardDTO) {
        return null;
    }

    @Override
    public List<StolenCardDTO> getStolenCardList() {
        return null;
    }

    @Override
    public DeleteStolenCardDTO deleteStolenCard(String cardNumber) {
        return null;
    }
}
