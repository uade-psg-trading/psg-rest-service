package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByExternalIdentifier(String externalIdentifier);
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByEmailIgnoreCaseAndTenantId(String email, String tenantId);
    Optional<User> findByIdAndBalances_tenantId(long id, String tenant);
}
