package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.JwtConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String generateJWT(String email){
        Instant expiration = Instant.now().plusMillis(300_000);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .expiresAt(expiration)
                .claim("scope", "USER")
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JwtConfig.ALGORITHM).build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        return jwt.getTokenValue();
    }

    public void decodeJWT(String jwt){

        jwtDecoder.decode(jwt);
    }

}
