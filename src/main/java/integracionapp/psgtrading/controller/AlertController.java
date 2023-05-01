package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.AlertDTO;
import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.model.Alert;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.AlertRepository;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private UserRepository userRepository;
    private JwtService jwtService;

    private AlertRepository alertRepository;

    private SymbolRepository symbolRepository;

    @Autowired
    public AlertController(UserRepository userRepository,
                           JwtService jwtService,
                           AlertRepository alertRepository,
                           SymbolRepository symbolRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.symbolRepository = symbolRepository;
        this.alertRepository = alertRepository;

    }

    @GetMapping("")
    public ResponseEntity<GenericDTO<List<Alert>>> getUserAlerts(@RequestHeader("Authorization") String jwt) {

        try {

            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);

            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);

            List<Alert> alerts = alertRepository.findByUser(user);

            return new ResponseEntity<>(GenericDTO.success(alerts), HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }

    }

    @PostMapping("")
    public ResponseEntity<GenericDTO<Alert>> createAlert(@RequestHeader("Authorization") String jwt,
                                                         @RequestBody AlertDTO alertDTO) {
        Alert.Operator operator;
        try {
            operator = Alert.Operator.valueOf(alertDTO.getOperator().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(GenericDTO.error("Invalid operator. Should be SELL or BUY"));
        }
        try {
            String token = alertDTO.getToken();
            Double amount = alertDTO.getAmount();
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);

            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);

            Symbol symbol = symbolRepository.findBySymbol(token);

            Alert alert = new Alert();
            alert.setUser(user);
            alert.setAmount(amount);
            alert.setSymbol(symbol);
            alert.setOperator(operator);

            alertRepository.save(alert);

            return new ResponseEntity<>(GenericDTO.success(alert), HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }

    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<GenericDTO<Alert>> deleteAlert(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable long alertId) {
        try {
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);

            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);

            Optional<Alert> alertOptional = alertRepository.findById(alertId);

            if (alertOptional.isEmpty()) {
                return ResponseEntity.status(400).body(GenericDTO.error("No alert founds for id " + alertId));
            }
            Alert alert = alertOptional.get();
            if (alert.getUser() != user) {
                return ResponseEntity.status(400).body(GenericDTO.error("Alert with id " + alertId + " does not " +
                        "belong to this user."));
            }
            alertRepository.delete(alert);

            return new ResponseEntity<>(GenericDTO.success(alert), HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }
}
