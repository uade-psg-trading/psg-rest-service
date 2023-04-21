package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.reactivestreams.Publisher;

import java.io.Serializable;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinResponseDto implements Serializable {



    @JsonProperty("data")
    private Data data;
    @JsonProperty("status")
    private Estado estado;

}
