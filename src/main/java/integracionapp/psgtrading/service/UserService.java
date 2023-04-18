package integracionapp.psgtrading.service;

import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User saveUser(String email, String name, String lastName, Integer dni, Location location, String password) {
        Optional<User> opt = userRepository.findByEmailIgnoreCaseOrDni(email, dni);
        if (opt.isPresent()) {
            throw new CustomRuntimeException(ErrorCode.INVALID_STATE, "Email Or DNI in use");
        }
        password = passwordEncoder.encode(password);
        email = email.toLowerCase();
        User user = new User(name, lastName, email, password, dni, location);
        return userRepository.save(user);
    }

    public User findById(long id){
        return userRepository.findById(id)
                .orElseThrow();
    }

    public User updateUser(String email, String password, String name, String lastName, int dni) {
        email = email.toLowerCase();
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
        if (null != password) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (null != name) {
            user.setFirstName(name);
        }
        if (null != lastName) {
            user.setLastName(lastName);
        }
        if ( 0 != dni) {
            user.setDni(dni);
        }
        return userRepository.save(user);
    }
}
