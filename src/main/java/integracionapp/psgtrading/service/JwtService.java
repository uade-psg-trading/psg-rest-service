package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.JwtConfig;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String generateJWT(JWTObjectDTO jwtObjectDTO){
        Instant expiration = Instant.now().plusMillis(300_000);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(jwtObjectDTO.getEmail())
                .expiresAt(expiration)
                .claim("scope", "USER")
                .claim("userID", jwtObjectDTO.getUserId())
                .claim("email", jwtObjectDTO.getEmail())
                .claim(JwtConfig.TENANT_CLAIM, jwtObjectDTO.getTenantId())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JwtConfig.ALGORITHM).build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        return jwt.getTokenValue();
    }

    public JWTObjectDTO decodeJWT(String token){

        Jwt jwt = jwtDecoder.decode(token.replace("Bearer ", ""));

        return new JWTObjectDTO(jwt.getClaim("email"), jwt.getClaim("userID"), jwt.getClaim(JwtConfig.TENANT_CLAIM));

    }

}
