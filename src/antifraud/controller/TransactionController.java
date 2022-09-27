package antifraud.controller;

import antifraud.dto.TransactionRequestDTO;
import antifraud.dto.TransactionResponseDTO;
import antifraud.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/antifraud/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> makeTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {

        if (transactionRequestDTO.getAmount() == null || transactionRequestDTO.getAmount() <= 0) return ResponseEntity.badRequest().build();

        return new ResponseEntity<>(transactionService.makeTransaction(transactionRequestDTO), HttpStatus.OK);
    }
}
