package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.JwtConfig;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class SessionService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final JwtService jwtService;
    public String login(String email, String password) {
        email = email.toLowerCase();
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request"));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request");
        }

        return jwtService.generateJWT(new JWTObjectDTO(email, user.getId(), user.getTenantId()));
    }
}
