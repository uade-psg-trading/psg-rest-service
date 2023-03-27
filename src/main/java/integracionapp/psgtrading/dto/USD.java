package integracionapp.psgtrading.dto;

import lombok.Data;

@Data
public class USD {
    private Double percent_change_30d;
    private Double fully_diluted_market_cap;
    private Double percent_change_1h;
    private String last_updated;
    private Double percent_change_24h;
    private Double market_cap;
    private Double volume_change_24h;
    private Double price;
    private Double volume_24h;
    private Integer market_cap_dominance;
    private Double percent_change_7d;
}
