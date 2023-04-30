package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.response.Yield;
import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.BalanceService;
import integracionapp.psgtrading.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/balances")
public class BalanceController {
    private final BalanceService balanceService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BalanceRepository balanceRepository;

    @Autowired
    public BalanceController(BalanceService balanceService, UserRepository userRepository, JwtService jwtService,
                             BalanceRepository balanceRepository) {
        this.userRepository = userRepository;
        this.balanceService = balanceService;
        this.jwtService = jwtService;
        this.balanceRepository = balanceRepository;
    }

    @GetMapping()
    public GenericDTO<List<Yield>> getAllByUser(@RequestHeader("Authorization") String jwt) {
        try {

            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);

            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
            List<Yield> yields = balanceService.getYieldsByUser(user);
            return GenericDTO.success(yields);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

    @GetMapping("/fiat")
    public ResponseEntity<GenericDTO<Balance>> getUserFiatBalance(@RequestHeader("Authorization") String jwt) {
        try {
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);

            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
            Balance fiatBalance = balanceRepository.findBySymbolIsTokenFalseAndUser(user);
            if (fiatBalance == null) {
                return ResponseEntity.status(400).body(GenericDTO.error("User does not have a fiat balance yet."));
            }
            return new ResponseEntity<>(GenericDTO.success(fiatBalance), HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

}
