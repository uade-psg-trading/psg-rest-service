package integracionapp.psgtrading.controller;


import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.LoginInfo;
import integracionapp.psgtrading.dto.login.LoginResponseDTO;
import integracionapp.psgtrading.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;
    @PostMapping
    public ResponseEntity<GenericDTO<LoginResponseDTO>> login(@RequestBody LoginInfo loginInfo ) {
        String jwt = sessionService.login(loginInfo.getEmail(), loginInfo.getPassword());
        String refreshToken = "";
        return ResponseEntity.ok(GenericDTO.success(new LoginResponseDTO(jwt, refreshToken)));
    }
}
