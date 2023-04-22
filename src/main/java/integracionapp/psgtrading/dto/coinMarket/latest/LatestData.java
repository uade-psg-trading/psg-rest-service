package integracionapp.psgtrading.dto.coinMarket.latest;

import com.fasterxml.jackson.annotation.JsonProperty;
import integracionapp.psgtrading.dto.coinMarket.coins.Coin;
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
public class LatestData implements Serializable {

    @JsonProperty("PSG")
    private Coin PSG;
    @JsonProperty("BAR")
    private Coin BAR;
    @JsonProperty("CITY")
    private Coin CITY;
    @JsonProperty("LAZIO")
    private Coin LAZIO;
    @JsonProperty("PORTO")
    private Coin PORTO;
    @JsonProperty("SANTOS")
    private Coin SANTOS;
    @JsonProperty("AFC")
    private Coin AFC;
    @JsonProperty("ACM")
    private Coin ACM;
    @JsonProperty("JUV")
    private Coin JUV;
    @JsonProperty("NAP")
    private Coin NAP;


}
