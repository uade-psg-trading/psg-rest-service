package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {

    @JsonProperty("PSG")
    private PSG PSG;

}
