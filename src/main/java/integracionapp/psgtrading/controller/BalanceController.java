package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.response.Yield;
import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.BalanceService;
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
@RequestMapping("/balances")
public class BalanceController {
    private final BalanceService balanceService;
    private final UserRepository userRepository;

    @Autowired
    public BalanceController(BalanceService balanceService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.balanceService = balanceService;
    }
    @GetMapping()
    public GenericDTO<List<Yield>> getAllByUser(@RequestBody Balance balance,
                                                 @PathVariable("external_identifier") String externalIdentifier) {
        try {
            User user = userRepository.findByExternalIdentifier(externalIdentifier).orElseThrow(EntityNotFoundException::new);
            List<Yield> yields = balanceService.getYieldsByUser(user);
            return GenericDTO.success(yields);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

}
