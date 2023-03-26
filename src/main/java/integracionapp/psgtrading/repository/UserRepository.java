package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByExternalIdentifier(long externalIdentifier);

}
