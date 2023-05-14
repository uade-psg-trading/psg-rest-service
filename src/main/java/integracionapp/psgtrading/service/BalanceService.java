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
    private final AlertService alertService;

    public List<Yield> getYieldsByUser(User user){

        List<Balance> balances = balanceRepository.findByUser(user);

        return balances.stream()
                .filter(balance -> balance.getSymbol().isToken())
                .map(balance -> new YieldBuilder(
                        balance,
                        newStockService.getCoinPrice(balance.getSymbol().getSymbol()),
                        !alertService.getCoinAlert(balance.getSymbol(), user).isEmpty()
                ).build())
                .toList();
    }

}

