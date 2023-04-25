package integracionapp.psgtrading.dto.coinMarket.response;

import lombok.Data;

@Data
public class CoinDTO {

    private Integer id;
    private String symbol;
    private String name;
    private Double price;
    private Double percent_change_24h;
    private Double volume_change_24h;

}
