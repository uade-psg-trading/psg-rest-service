package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.model.Transaction;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.TransactionRepository;
import integracionapp.psgtrading.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<GenericDTO<List<Transaction>>> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>(transactionRepository.findAll());
        return new ResponseEntity<>(GenericDTO.success(transactions), HttpStatus.OK);

    }

    @GetMapping("/{external_identifier}")
    public ResponseEntity<GenericDTO<List<Transaction>>> getUserTransactions(@PathVariable("external_identifier") String externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier).orElseThrow(EntityNotFoundException::new);
            List<Transaction> transactions = new ArrayList<>(transactionRepository.findByUser(user));
            return new ResponseEntity<>(GenericDTO.success(transactions), HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

    @PostMapping("/{external_identifier}")
    public ResponseEntity<GenericDTO<Transaction>> createTransaction(@RequestBody Transaction requestTransaction,
                                                         @PathVariable("external_identifier") String externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier).orElseThrow(EntityNotFoundException::new);
            Transaction transaction = transactionRepository
                    .save(new Transaction(requestTransaction.getToken(), requestTransaction.getQuantity(), requestTransaction.getPrice(),
                            requestTransaction.getBalance(), requestTransaction.getOperation(), LocalDateTime.now(), user));
            return new ResponseEntity<>(GenericDTO.success(transaction), HttpStatus.CREATED);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }

    }
}
