package integracionapp.psgtrading.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    @NotNull
    @Min(1)
    private double amount;
    private PaymenMethod paymentMethod; // tarjeta de credito o transferencia

}
