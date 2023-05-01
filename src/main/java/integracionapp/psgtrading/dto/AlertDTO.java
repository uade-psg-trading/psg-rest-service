package integracionapp.psgtrading.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertDTO {
    @NotNull
    @Positive
    private double amount;
    @NotNull
    private String token;
    @NotNull
    private String operator;

}