package integracionapp.psgtrading.dto.coins.latest;

import com.fasterxml.jackson.annotation.JsonProperty;
import integracionapp.psgtrading.dto.coins.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
