package integracionapp.psgtrading.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.login.LoginResponseDTO;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.Tenant;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.model.provider.LoginProvider;
import integracionapp.psgtrading.repository.TenantRepository;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final TenantRepository tenantRepository;

    public LoginResponseDTO login(String email, String password) {
        if( email == null){
            throw new CustomRuntimeException(ErrorCode.INVALID_PARAMETER, "You must provide a valid email.");
        }
        email = email.toLowerCase();
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request"));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomRuntimeException(ErrorCode.FORBIDDEN, "You do not have permissions for this request");
        }

        String jwt = jwtService.generateJWT(new JWTObjectDTO(email, user.getId(), user.getTenant().getTenantId()));
        String refreshToken = "";
        return new LoginResponseDTO(jwt, refreshToken, user.getEmail(), user.getTenant().getTenantId());
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

        Tenant tenantId = tenantRepository.findByTenantId(tenant);
        if( tenantId == null){
            throw new CustomRuntimeException(ErrorCode.INVALID_PARAMETER, "You must provide a valid tenant.");
        }

        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        String jwt;
        JWTObjectDTO jwtObjectDTO;
        if (!user.isPresent()) {
            String name = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            User newUser = userService.saveUser(email, name, lastName, null, null, payload.getSubject(), tenant);
            jwtObjectDTO = new JWTObjectDTO(email, newUser.getId(), tenant);
        } else {
            User currentUser = user.get();
            jwtObjectDTO = new JWTObjectDTO(email, currentUser.getId(), tenant);
        }

        jwt = jwtService.generateJWT(jwtObjectDTO);
        return new LoginProvider(email, "google", jwt, "");
    }

}
