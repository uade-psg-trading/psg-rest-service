package integracionapp.psgtrading.controller;


import integracionapp.psgtrading.dto.LoginInfo;
import integracionapp.psgtrading.service.SessionService;
import lombok.AllArgsConstructor;
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
    public String login(@RequestBody LoginInfo loginInfo ) {
        return sessionService.login(loginInfo.getEmail(), loginInfo.getPassword());
    }
}
