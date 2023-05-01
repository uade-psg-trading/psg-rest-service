package integracionapp.psgtrading.service;

import integracionapp.psgtrading.dto.login.LoginResponseDTO;
import integracionapp.psgtrading.exception.CustomRuntimeException;
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
    @MockBean
    private UserService userService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Test
    void login_ok() {
        String email = "jwick@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String tenant = "TENANT";
        String pass = passwordEncoder.encode("password");
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location, tenant);

        when(userRepository.findByEmailIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(mockUser));

        LoginResponseDTO response = sessionService.login(email,"password");

        Assertions.assertNotNull(response);
    }

    @Test
    void login_WrongEmail() {
        Assertions.assertThrows(CustomRuntimeException.class
                ,() -> sessionService.login("wrong@email.com","password"));
    }

    @Test
    void login_WrongPass(){
        String email = "jwick@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String pass = "password";
        String tenant = "TENANT";
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location, tenant);

        when(userRepository.findByEmailIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(mockUser));

        Assertions.assertThrows(CustomRuntimeException.class
                ,() -> sessionService.login("wrong@email.com","password"));
    }

}
