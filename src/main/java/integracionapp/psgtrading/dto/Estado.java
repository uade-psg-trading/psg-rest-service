package integracionapp.psgtrading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Estado  implements Serializable {

    private String error_message;
    private Integer elapsed;
    private Integer credit_count;
    private Integer error_code;
    private String timestamp;
    private String notice;

}
