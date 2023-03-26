package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
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
public class BalanceController {

    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/balance")
    public ResponseEntity<List<Balance>> getTokenBalances(@RequestParam(required = false) String token,
                                                          @RequestParam(required = false) Long externalIdentifier) {
        List<Balance> balances = new ArrayList<>();
        try {
            if (token != null && externalIdentifier == null) {
                balances = new ArrayList<>(balanceRepository.findByToken(token));
            } else if (token != null) {
                User user = userRepository.findByExternalIdentifier(externalIdentifier);
                balances = new ArrayList<>(balanceRepository.findByTokenAndUser(token, user));
            } else if (externalIdentifier != null) {
                User user = userRepository.findByExternalIdentifier(externalIdentifier);
                if (user != null) {
                    balances = balanceRepository.findByUser(user);
                }
            } else {
                balances = new ArrayList<>(balanceRepository.findAll());
            }
            if (balances.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(balances, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/balance/{external_identifier}")
    public ResponseEntity<Balance> createBalance(@RequestBody Balance balance,
                                                 @PathVariable("external_identifier") long externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Balance _balance = balanceRepository
                    .save(new Balance(balance.getToken(), balance.getBalance(), user, LocalDateTime.now()));
            return new ResponseEntity<>(_balance, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
