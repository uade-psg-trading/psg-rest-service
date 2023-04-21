package integracionapp.psgtrading.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private String jwt;
    private String refreshToken;
}
