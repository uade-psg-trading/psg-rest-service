package integracionapp.psgtrading.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import integracionapp.psgtrading.configuration.JwtConfig;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.model.provider.LoginProvider;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public String login(String email, String password) {
        email = email.toLowerCase();
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request"));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request");
        }

        Jwt jwt = createJwtSession(user);
        return jwt.getTokenValue();
    }

    public LoginProvider loginThroughProvider(String idToken, String tenant) throws IOException, GeneralSecurityException {
        GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(idToken);
        if (googleIdToken == null) {
            throw new CustomRuntimeException(ErrorCode.FORBIDDEN, "El token enviado no es valido");
        }

        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        String email = payload.getEmail();
        if (email == null) {
            throw new CustomRuntimeException(ErrorCode.FORBIDDEN, "El token enviado no es valido");
        }

        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        Jwt jwt;
        if (!user.isPresent()) {
            System.out.println("User not exists");
            // el usuario no existe, lo creamos
            String name = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            User newUser = userService.saveUser(email, name, lastName, null, null, payload.getSubject(), tenant);
            jwt = createJwtSession(newUser);
        } else {
            System.out.println("User exists");
            jwt = createJwtSession(user.get());
        }

        return new LoginProvider(email, "google", jwt.getTokenValue(), "");
    }

    private Jwt createJwtSession(User user) {
        Instant expiration = Instant.now().plusMillis(300_000);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .expiresAt(expiration)
                .claim("scope", "USER")
                .claim(JwtConfig.TENANT_CLAIM, user.getTenantId())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JwtConfig.ALGORITHM).build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        return jwt;
    }
}
