package integracionapp.psgtrading.dto.coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status implements Serializable {

    @JsonProperty("error_message")
    private String error_message;
    @JsonProperty("elapsed")
    private Integer elapsed;
    @JsonProperty("credit_count")
    private Integer credit_count;
    @JsonProperty("error_code")
    private Integer error_code;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("notice")
    private String notice;

}
