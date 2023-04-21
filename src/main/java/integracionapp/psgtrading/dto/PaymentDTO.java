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
    private Integer cardExpirationDateMonth;
    private Integer cardExpirationDateDay;
    private Integer cardSecurityCode;
    private String bankName;
    private String accountNumber;
    @Schema(example = "John Wick")
    private String accountHolderName;
    @Schema(example = "jwick@mail.com")
    private String email;

}
