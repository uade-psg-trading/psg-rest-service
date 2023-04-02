package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.JwtConfig;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    public String login(String email, String password) {
        email = email.toLowerCase();
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request"));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request");
        }

        Instant expiration = Instant.now().plusMillis(120_000);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .expiresAt(expiration)
                .claim("scope", "USER")
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JwtConfig.ALGORITHM).build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        return jwt.getTokenValue();
    }
}
