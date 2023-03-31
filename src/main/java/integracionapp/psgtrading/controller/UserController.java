package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.NewUser;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<User> getAllUsers() {
        return null;
    }

    @PostMapping
    public User createUser(@RequestBody NewUser user) {
        return userService.saveUser(user.getEmail(), user.getName(),
                user.getLastName(), user.getDni(), user.getLocation(), user.getPassword());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){ return null;}

}
