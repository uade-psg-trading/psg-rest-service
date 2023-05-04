package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.PaymenMethod;
import integracionapp.psgtrading.dto.PaymentDTO;
import integracionapp.psgtrading.model.Income;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.JwtService;
import integracionapp.psgtrading.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @PostMapping
    public ResponseEntity<GenericDTO<String>> processPayment(@RequestBody @Valid PaymentDTO p,
                                                 @RequestHeader("Authorization") String jwt) {
        JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);
        User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        if(p.getPaymentMethod() != PaymenMethod.TRANSFER && p.getPaymentMethod() != PaymenMethod.CREDIT_CARD){
            return ResponseEntity.status(400).body(GenericDTO.error("Método de pago no válido"));
        }
        Income incomePay = new Income(p.getAmount(),p.getPaymentMethod(), user, user.getTenant().getTenantId());
        paymentService.transferPayment(incomePay, user);

        return new ResponseEntity<>(GenericDTO.success("Payment processed correctly."), HttpStatus.CREATED);
    }

}
