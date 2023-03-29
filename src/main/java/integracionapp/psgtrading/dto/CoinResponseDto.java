package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.reactivestreams.Publisher;

@lombok.Data
@Builder
public class CoinResponseDto {

    @JsonProperty("data")
    private Data data;
    @JsonProperty("status")
    private Estado estado;

}
