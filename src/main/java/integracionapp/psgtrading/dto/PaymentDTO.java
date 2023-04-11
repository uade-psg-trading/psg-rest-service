package integracionapp.psgtrading.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
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
    @NotNull
    @Min(16)
    private String cardNumber;
    @Max(2)
    private Integer cardExpirationDateMonth;
    @Max(2)
    private Integer cardExpirationDateDay;
    @Max(3)
    private String cardSecurityCode;
    private String bankName;
    private String accountNumber;
    @Schema(example = "John Wick")
    private String accountHolderName;

}
