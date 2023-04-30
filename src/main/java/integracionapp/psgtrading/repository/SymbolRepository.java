package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    Symbol findBySymbol(String token);

    Symbol findByIsTokenFalse();

}
