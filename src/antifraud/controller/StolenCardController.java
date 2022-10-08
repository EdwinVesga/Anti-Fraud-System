package antifraud.controller;

import antifraud.dto.DeleteStolenCardDTO;
import antifraud.dto.StolenCardDTO;
import antifraud.service.StolenCardService;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("hasRole('SUPPORT')")
@Validated
@RequestMapping("/api/antifraud/stolencard")
public class StolenCardController {

    private final StolenCardService stolenCardService;

    @Autowired
    public StolenCardController(StolenCardService stolenCardService) {
        this.stolenCardService = stolenCardService;
    }

    @PostMapping
    public ResponseEntity<StolenCardDTO> addStolenCard(@Valid @RequestBody StolenCardDTO stolenCardDTO) {
        return new ResponseEntity<>(stolenCardService.addStolenCard(stolenCardDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StolenCardDTO>> getStolenCardList() {
        return new ResponseEntity<>(stolenCardService.getStolenCardList(), HttpStatus.OK);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<DeleteStolenCardDTO> deleteStolenCard(@PathVariable @CreditCardNumber String number) {
        return new ResponseEntity<>(stolenCardService.deleteStolenCard(number), HttpStatus.OK);
    }
}
