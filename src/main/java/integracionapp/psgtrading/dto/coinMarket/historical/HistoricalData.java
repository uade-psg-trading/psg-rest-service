package integracionapp.psgtrading.dto.coinMarket.historical;

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
public class HistoricalData implements Serializable {

    @JsonProperty("PSG")
    private PSG psg;

}
