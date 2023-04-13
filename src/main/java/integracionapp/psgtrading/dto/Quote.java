package integracionapp.psgtrading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Quote {
    @JsonProperty("USD")
    private USD USD;

}
