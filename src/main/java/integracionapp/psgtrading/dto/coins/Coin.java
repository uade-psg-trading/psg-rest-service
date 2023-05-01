package integracionapp.psgtrading.dto.coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coin implements Serializable {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("circulating_supply")
    private Integer circulating_supply;
    @JsonProperty("last_updated")
    private String last_updated;
    @JsonProperty("is_active")
    private Integer is_active;
    @JsonProperty("total_supply")
    private Integer total_supply;
    @JsonProperty("cmc_rank")
    private Integer cmc_rank;
    @JsonProperty("self_reported_circulating_supply")
    private String self_reported_circulating_supply;
    @JsonProperty("platform")
    private Platform platform;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("date_added")
    private String date_added;
    @JsonProperty("quote")
    private Quote quote;
    @JsonProperty("num_market_pairs")
    private Integer num_market_pairs;
    @JsonProperty("name")
    private String name;
    @JsonProperty("max_supply")
    private Integer max_supply;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("self_reported_market_cap")
    private String self_reported_market_cap;
    @JsonProperty("is_fiat")
    private String is_fiat;
    @JsonProperty("slug")
    private String slug;

}
