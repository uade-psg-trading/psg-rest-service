package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Transaction;
import integracionapp.psgtrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
