package integracionapp.psgtrading.dto.coins.response;

import integracionapp.psgtrading.model.TokenPrice;
import lombok.Data;

@Data
public class CoinDTO {
    private TokenPrice tokenPrice;
    private String symbol;

}
