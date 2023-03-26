package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.model.Transaction;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.TransactionRepository;
import integracionapp.psgtrading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {

            List<Transaction> transactions = new ArrayList<>(transactionRepository.findAll());
            if (transactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transaction/{external_identifier}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable("external_identifier") long externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Transaction> transactions = new ArrayList<>(transactionRepository.findByUser(user));
            if (transactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transaction/{external_identifier}")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction,
                                                         @PathVariable("external_identifier") long externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Transaction _transaction = transactionRepository
                    .save(new Transaction(transaction.getToken(), transaction.getQuantity(), transaction.getPrice(),
                            transaction.getBalance(), transaction.getOperation(), LocalDateTime.now(), user));
            return new ResponseEntity<>(_transaction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
