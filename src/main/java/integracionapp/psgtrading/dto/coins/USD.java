package integracionapp.psgtrading.dto.coins;

import lombok.Data;

@Data
public class USD  {

    private double price;
    private double volume_24h;
    private double volume_change_24h;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;
    private double percent_change_30d;
    private double percent_change_60d;
    private double percent_change_90d;
    private double market_cap;
    private double market_cap_dominance;
    private Double fully_diluted_market_cap;
    private Double tvl;
    private String last_updated;


}
