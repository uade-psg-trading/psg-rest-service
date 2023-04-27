package integracionapp.psgtrading.controller;


import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.LoginInfo;
import integracionapp.psgtrading.dto.login.LoginResponseDTO;
import integracionapp.psgtrading.dto.provider.ProviderRequestDTO;
import integracionapp.psgtrading.model.provider.LoginProvider;
import integracionapp.psgtrading.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@AllArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<GenericDTO<LoginResponseDTO>> login(@RequestBody LoginInfo loginInfo) {
        String jwt = sessionService.login(loginInfo.getEmail(), loginInfo.getPassword());
        String refreshToken = "";
        return ResponseEntity.ok(GenericDTO.success(new LoginResponseDTO(jwt, refreshToken, loginInfo.getEmail())));
    }

    @PostMapping("/{provider}")
    public ResponseEntity<GenericDTO<LoginResponseDTO>> providerLogin(@PathVariable("provider") String provider, @RequestBody ProviderRequestDTO providerRequestDTO)
            throws GeneralSecurityException, IOException {

        if (provider.equalsIgnoreCase("google")) {
            String tenant = providerRequestDTO.getTenant() == null ? "psg" : providerRequestDTO.getTenant();
            LoginProvider loginProvider = sessionService.loginThroughProvider(providerRequestDTO.getToken(), tenant);
            return ResponseEntity.ok(GenericDTO.success(new LoginResponseDTO(loginProvider.getJwt(), loginProvider.getRefreshToken(), loginProvider.getEmail())));
        }

        return ResponseEntity.notFound().build();
    }
}
