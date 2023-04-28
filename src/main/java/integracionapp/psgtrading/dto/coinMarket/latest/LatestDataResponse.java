package integracionapp.psgtrading.dto.coinMarket.latest;

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
public class LatestDataResponse  implements Serializable {

    @JsonProperty("data")
    private LatestData data;
    @JsonProperty("status")
    private Status status;

}
