package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.TokenPrice;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.TokenPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {
    @Autowired
    private NewStockService newStockService;
    @Autowired
    private SymbolRepository symbolRepository;
    @Autowired
    private TokenPriceRepository tokenPriceRepository;
    @Autowired
    private AlertService alertService;

    @Scheduled(fixedDelay = 600000) // schedule to run every 10 minutes (in milliseconds)
    public void updatePrices() {
        List<Symbol> tokens = symbolRepository.findByIsTokenTrue();
        List<TokenPrice> prices = new ArrayList<>();

        for (Symbol token : tokens) {
            TokenPrice price = newStockService.getCoinPrice(token.getSymbol());
            price.setSymbol(token);
            prices.add(price);
        }
        tokenPriceRepository.saveAll(prices);
        alertService.sendAlerts();
    }

}
