package antifraud.controller;

import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import antifraud.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('MERCHANT')")
@RequestMapping("/api/antifraud/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> makeTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        return new ResponseEntity<>(transactionService.makeTransaction(transactionRequestDTO), HttpStatus.OK);
    }
}
