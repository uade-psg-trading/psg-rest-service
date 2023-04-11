package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.PaymenMethod;
import integracionapp.psgtrading.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody PaymentDTO payment) {
        // Lógica para procesar el pago según el método de pago seleccionado
        if (payment.getPaymentMethod().equals(PaymenMethod.CREDIT_CARD)) {
            // Lógica para procesar el pago con tarjeta de crédito
        } else if (payment.getPaymentMethod().equals(PaymenMethod.TRANSFER)) {
            // Lógica para procesar el pago con transferencia
        } else {
            return ResponseEntity.badRequest().body("Método de pago no válido");
        }
        return ResponseEntity.ok("Pago procesado exitosamente");
    }
}
