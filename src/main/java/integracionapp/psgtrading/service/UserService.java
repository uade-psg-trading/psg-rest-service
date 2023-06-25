package integracionapp.psgtrading.service;

import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.Tenant;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.TenantRepository;
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
    private final TenantRepository tenantRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User saveUser(String email, String name, String lastName, Integer dni,
                         Location location, String password, String tenant) {
        if (tenant == null) {
            // Cuando los usuarios no usan ni psg, lazio, entonces el tenant es default
            tenant = "trading";
        }
        Tenant tenantId = tenantRepository.findByTenantId(tenant);
        if(tenantId == null){
            throw new CustomRuntimeException(ErrorCode.INVALID_PARAMETER, "Invalid tenant");
        }

        Optional<User> opt = userRepository.findByEmailIgnoreCaseAndTenant(email, tenantId);
        if (opt.isPresent()) {
            throw new CustomRuntimeException(ErrorCode.INVALID_STATE, "Email in use");
        }
        password = passwordEncoder.encode(password);
        email = email.toLowerCase();
        User user = new User(name, lastName, email, password, dni, location);
        user.setTenant(tenantId);
        return userRepository.save(user);
    }

    public User updateUser(User user, String password, String name, String lastName, int dni, Location location) {
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
        user.setLocation(location);
        return userRepository.save(user);
    }
}
