package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.TokenPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenPriceRepository extends JpaRepository<TokenPrice, Long> {

    TokenPrice findFirstBySymbolOrderByUpdateTimeDesc(Symbol symbol);
}


