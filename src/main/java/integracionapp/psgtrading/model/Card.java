package integracionapp.psgtrading.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Card {
    private String cardNumber;
    private Integer cardExpirationDateMonth;
    private Integer cardExpirationDateDay;
    private String cardSecurityCode;
    private String bankName;
    private String accountNumber;
    private String accountHolderName;
}
