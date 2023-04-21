package integracionapp.psgtrading.dto.coinMarket.historical;

import com.fasterxml.jackson.annotation.JsonProperty;
import integracionapp.psgtrading.dto.coinMarket.Status;
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
public class PSG implements Serializable {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("is_active")
    private Integer is_active;
    @JsonProperty("is_fiat")
    private String is_fiat;
    @JsonProperty("quotes")
    private List<Quotes> quotes;


}
