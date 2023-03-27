package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = new ArrayList<>(userRepository.findAll());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try{
            User _user = userRepository
                    .save(new User(user.getFirstName(), user.getLastName(), user.getExternalIdentifier()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException exc){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.", exc);
        }

    }
}
