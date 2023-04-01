package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.NewUser;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<User> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PostMapping
    public User createUser(@RequestBody NewUser user) {
        return userService.saveUser(user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getDni(), user.getLocation(), user.getPassword());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){ return userService.findById(id);}

}
