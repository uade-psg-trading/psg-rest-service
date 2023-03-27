package integracionapp.psgtrading.dto;

import lombok.Data;

@Data
public class Estado {

    private String error_message;
    private Integer elapsed;
    private Integer credit_count;
    private Integer error_code;
    private String timestamp;
    private String notice;

}
