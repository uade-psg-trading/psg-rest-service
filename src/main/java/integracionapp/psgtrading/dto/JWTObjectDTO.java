package integracionapp.psgtrading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTObjectDTO {

    private String email;
    private long userId;
    private String tenantId;

}
