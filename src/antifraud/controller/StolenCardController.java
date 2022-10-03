package antifraud.controller;

import antifraud.dto.DeleteStolenCardDTO;
import antifraud.dto.StolenCardDTO;
import antifraud.service.StolenCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud/stolencard")
public class StolenCardController {

    private final StolenCardService stolenCardService;

    @Autowired
    public StolenCardController(StolenCardService stolenCardService) {
        this.stolenCardService = stolenCardService;
    }

    @PostMapping
    public ResponseEntity<StolenCardDTO> addStolenCard(StolenCardDTO stolenCardDTO) {
        return new ResponseEntity<>(stolenCardService.addStolenCard(stolenCardDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StolenCardDTO>> getStolenCardList() {
        return new ResponseEntity<>(stolenCardService.getStolenCardList(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteStolenCardDTO> deleteStolenCard(String cardNumber) {
        return new ResponseEntity<>(stolenCardService.deleteStolenCard(cardNumber), HttpStatus.OK);
    }
}
