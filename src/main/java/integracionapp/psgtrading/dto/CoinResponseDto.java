package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.reactivestreams.Publisher;

@lombok.Data
public class CoinResponseDto {

    @JsonProperty("data")
    private Data data;
    @JsonProperty("status")
    private Estado estado;

}
