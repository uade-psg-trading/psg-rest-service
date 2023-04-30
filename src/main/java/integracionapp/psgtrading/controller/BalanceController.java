package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.response.Yield;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.BalanceService;
import integracionapp.psgtrading.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public BalanceController(BalanceService balanceService, UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.balanceService = balanceService;
        this.jwtService = jwtService;
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

}
