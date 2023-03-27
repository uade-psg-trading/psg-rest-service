package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Quote {
    @JsonProperty("USD")
    private USD USD;

}
