package integracionapp.psgtrading.dto.coinMarket.historical;

import com.fasterxml.jackson.annotation.JsonProperty;
import integracionapp.psgtrading.dto.coinMarket.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalDataResponse implements Serializable {

    @JsonProperty("data")
    private HistoricalData data;
    @JsonProperty("status")
    private Status status;

}
