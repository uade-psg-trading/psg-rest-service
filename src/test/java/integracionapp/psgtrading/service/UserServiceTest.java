package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.mockito.Mockito.*;

@SpringBootTest
 class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void create_OK() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        User user = userService.saveUser("jhon@email.com","Jhon", "Wick",
                11111, new Location(),"password");
        Assertions.assertNotNull(user);
    }

}
