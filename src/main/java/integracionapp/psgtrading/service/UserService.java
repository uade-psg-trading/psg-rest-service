package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<User> opt = userRepository.findByEmailIgnoreCase(email);
        if (opt.isPresent()) {
            throw new IllegalArgumentException("Email in use");
        }
        password = passwordEncoder.encode(password);
        email = email.toLowerCase();
        User user = new User(name, lastName, email, password, dni, location);
        return userRepository.save(user);
    }
}
