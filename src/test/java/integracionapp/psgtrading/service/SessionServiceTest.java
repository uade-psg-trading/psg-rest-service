package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class SessionServiceTest {
    @Autowired
    private SessionService sessionService;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Test
    void login_ok() {
        String email = "jwick@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String pass = passwordEncoder.encode("password");
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location);

        when(userRepository.findByEmailIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(mockUser));

        String tocken = sessionService.login(email,"password");

        Assertions.assertNotNull(tocken);
    }

}