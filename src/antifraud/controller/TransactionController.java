package antifraud.controller;

import antifraud.dto.TransactionFeedbackDTO;
import antifraud.dto.TransactionHistoryDTO;
import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import antifraud.service.TransactionService;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/antifraud")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('MERCHANT')")
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponseDTO> makeTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        return new ResponseEntity<>(transactionService.makeTransaction(transactionRequestDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @PutMapping("/transaction")
    public ResponseEntity<TransactionHistoryDTO> putTransactionFeedback(@Valid @RequestBody TransactionFeedbackDTO transactionFeedbackDTO) {
        return new ResponseEntity<>(transactionService.putTransactionFeedback(transactionFeedbackDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @GetMapping("/history")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistory() {
        return new ResponseEntity<>(transactionService.getTransactionHistory(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @GetMapping("/history/{number}")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistoryByNumber(@CreditCardNumber @PathVariable("number") String number) {
        return new ResponseEntity<>(transactionService.getTransactionHistoryByNumber(number), HttpStatus.OK);
    }
}
