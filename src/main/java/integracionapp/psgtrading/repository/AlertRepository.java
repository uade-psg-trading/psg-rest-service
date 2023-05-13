package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Alert;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUser(User user);

    Optional<Alert> findBySymbolAndUser(Symbol symbol, User user);
}


