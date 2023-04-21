package integracionapp.psgtrading.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.dto.PaymenMethod;
import integracionapp.psgtrading.dto.PaymentDTO;
import integracionapp.psgtrading.model.Card;
import integracionapp.psgtrading.model.Income;
import integracionapp.psgtrading.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;
    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody @Valid PaymentDTO p) {
        if (p.getPaymentMethod().equals(PaymenMethod.CREDIT_CARD)) {
           Card d = new Card(p.getCardNumber(), p.getCardExpirationDateMonth(),
                   p.getCardExpirationDateDay(),p.getCardSecurityCode(),p.getBankName(),p.getAccountNumber(),
                   p.getAccountHolderName());
            Income incomePay = new Income(getPaimentID(p.getCardNumber()),p.getAmount(),
                    LocalDate.now(), p.getPaymentMethod(), p.getEmail());
            paymentService.creditCardPayment(incomePay, d);
        } else if (p.getPaymentMethod().equals(PaymenMethod.TRANSFER)) {
            Income incomePay = new Income(p.getAccountHolderName(), p.getAmount(),
                    LocalDate.now(),p.getPaymentMethod(),p.getEmail());
            paymentService.transferPayment(incomePay);
        } else {
            return ResponseEntity.badRequest().body("Método de pago no válido");
        }
        return ResponseEntity.ok("Pago procesado exitosamente");
    }

    private String getPaimentID(String s) {
        return s.substring(s.length() - 4 );
    }
}
