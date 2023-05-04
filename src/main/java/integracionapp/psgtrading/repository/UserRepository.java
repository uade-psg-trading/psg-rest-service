package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Tenant;
import integracionapp.psgtrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByEmailIgnoreCaseAndTenant(String email, Tenant tenant);
}
