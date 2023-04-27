package integracionapp.psgtrading.model.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@lombok.Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class LoginProvider {
    private String email;
    private String provider;
    private String jwt;
    private String refreshToken;
}
