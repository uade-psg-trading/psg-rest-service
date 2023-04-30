package integracionapp.psgtrading.dto.coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Quote{
    @JsonProperty("USD")
    private USD USD;

}
