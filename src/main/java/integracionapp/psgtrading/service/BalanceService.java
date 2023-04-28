package integracionapp.psgtrading.service;


import integracionapp.psgtrading.dto.response.Yield;
import integracionapp.psgtrading.dto.response.YieldBuilder;
import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final NewStockService newStockService;

    public List<Yield> getYieldsByUser(User user){

        List<Balance> balances = this.balanceRepository.findByUser(user);

        return balances.stream().map(
                b -> new YieldBuilder(b, this.newStockService.getCoinPrice(b.getSymbol()))
                        .build()
        ).toList();
    }

}

