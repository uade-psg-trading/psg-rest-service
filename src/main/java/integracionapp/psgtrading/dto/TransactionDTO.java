package integracionapp.psgtrading.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    @NotNull
    @Positive
    private double quantity;
    @NotNull
    private String token;

}
