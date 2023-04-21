package integracionapp.psgtrading.dto.coinMarket.historical;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class USD implements Serializable {

    @JsonProperty("market_cap")
    private Double market_cap;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("volume_24h")
    private Double volume_24h;
    @JsonProperty("circulating_supply")
    private Double circulating_supply;
    @JsonProperty("total_supply")
    private Double total_supply;
    @JsonProperty("timestamp")
    private String timestamp;

}
