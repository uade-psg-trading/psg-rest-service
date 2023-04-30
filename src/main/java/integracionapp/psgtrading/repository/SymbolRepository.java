package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    Symbol findBySymbol(String token);

    Symbol findByIsTokenFalse();

    List<Symbol> findAll();
}
