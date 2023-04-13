package integracionapp.psgtrading.service;

import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
 class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void create_OK() {
        String email = "jhon@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String pass = "password";
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location);
        when(userRepository.save(any(User.class)))
                .thenReturn(mockUser);

        User user = userService.saveUser(email,firstName, lastName,
                dni,location,pass);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(firstName,user.getFirstName());
        Assertions.assertEquals(lastName,user.getLastName());
        Assertions.assertEquals(email,user.getEmail());
        Assertions.assertEquals(location,user.getLocation());
    }

    @Test
    void create_EmailInUse() {
        when(userRepository.findByEmailIgnoreCase(any())).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(CustomRuntimeException.class
                , () -> userService.saveUser("any@mail.com", "", ""
                ,999, null, ""));
    }

    @Test
    void findById_OK() {
        String email = "jhon@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String pass = "password";
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location);

        when(userRepository.findById(any(long.class))).thenReturn(Optional.of(mockUser));

        Assertions.assertEquals(mockUser, userService.findById(1));

    }

    @Test
    void updateUser_OK() {
        String email = "jhon@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String pass = "password";
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location);
        User mockUpdatedUser = new User("July",lastName,email,pass,dni,location);

        when(userRepository.findByEmailIgnoreCase(any())).thenReturn(Optional
                        .of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUpdatedUser);

        Assertions.assertEquals(mockUpdatedUser,userService.updateUser(email,"July"
                ,lastName,pass,dni));
    }

}
