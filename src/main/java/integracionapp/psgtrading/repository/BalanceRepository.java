package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BalanceRepository extends JpaRepository<Balance, Long> {
    List<Balance> findByUser(User user);

    List<Balance> findByToken(String token);

    List<Balance> findByTokenAndUser(String token, User user);
}
