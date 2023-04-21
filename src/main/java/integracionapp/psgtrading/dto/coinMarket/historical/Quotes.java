package integracionapp.psgtrading.dto.coinMarket.historical;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quotes implements Serializable {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("quote")
    private Quote quote;

}
