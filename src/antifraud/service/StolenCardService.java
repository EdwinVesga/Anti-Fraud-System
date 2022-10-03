package antifraud.service;

import antifraud.dto.DeleteStolenCardDTO;
import antifraud.dto.StolenCardDTO;

import java.util.List;

public interface StolenCardService {

    StolenCardDTO addStolenCard(StolenCardDTO stolenCardDTO);

    List<StolenCardDTO> getStolenCardList();

    DeleteStolenCardDTO deleteStolenCard(String cardNumber);
}
