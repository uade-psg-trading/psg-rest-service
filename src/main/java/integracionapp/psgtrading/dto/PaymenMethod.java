package integracionapp.psgtrading.dto;

import lombok.Getter;

@Getter
public enum PaymenMethod {
    CREDIT_CARD("tarjeta de credito"),
    TRANSFER("transferencia");

    private  final String displayName;

    PaymenMethod(String displayName) {
        this.displayName = displayName;
    }
}
