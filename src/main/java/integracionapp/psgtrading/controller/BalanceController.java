package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
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
@RequestMapping("/balance")
public class BalanceController {
    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;

    @Autowired
    public BalanceController(BalanceRepository balanceRepository, UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }


    @GetMapping()
    public ResponseEntity<List<Balance>> getTokenBalances(@RequestParam(required = false) String token,
                                                          @RequestParam(required = false) Long externalIdentifier) {
        List<Balance> balances;
        try {
            if (token != null && externalIdentifier == null) {
                balances = new ArrayList<>(balanceRepository.findByToken(token));
            } else if (token != null) {
                User user = userRepository.findByExternalIdentifier(externalIdentifier).orElseThrow(IllegalArgumentException::new);
                balances = new ArrayList<>(balanceRepository.findByTokenAndUser(token, user));
            } else if (externalIdentifier != null) {
                User user = userRepository.findByExternalIdentifier(externalIdentifier).orElseThrow(IllegalArgumentException::new);
                balances = balanceRepository.findByUser(user);
            } else {
                balances = new ArrayList<>(balanceRepository.findAll());
            }
            if (balances.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(balances, HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

    @PostMapping("/{external_identifier}")
    public ResponseEntity<Balance> createBalance(@RequestBody Balance balance,
                                                 @PathVariable("external_identifier") long externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier).orElseThrow(EntityNotFoundException::new);
            Balance _balance = balanceRepository
                    .save(new Balance(balance.getToken(), balance.getBalance(), user, LocalDateTime.now()));
            return new ResponseEntity<>(_balance, HttpStatus.CREATED);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

}
