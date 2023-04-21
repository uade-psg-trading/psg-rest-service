package integracionapp.psgtrading.dto;

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

    @JsonProperty("percent_change_30d")
    private Double percent_change_30d;
    @JsonProperty("fully_diluted_market_cap")
    private Double fully_diluted_market_cap;
    @JsonProperty("percent_change_1h")
    private Double percent_change_1h;
    @JsonProperty("last_updated")
    private String last_updated;
    @JsonProperty("percent_change_24h")
    private Double percent_change_24h;
    @JsonProperty("market_cap")
    private Double market_cap;
    @JsonProperty("volume_change_24h")
    private Double volume_change_24h;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("volume_24h")
    private Double volume_24h;
    @JsonProperty("market_cap_dominance")
    private Integer market_cap_dominance;
    @JsonProperty("percent_change_7d")
    private Double percent_change_7d;
}
