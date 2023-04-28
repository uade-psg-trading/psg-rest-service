package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@lombok.Data
@Builder
public class Data {

    @JsonProperty("PSG")
    private PSG PSG;

}
