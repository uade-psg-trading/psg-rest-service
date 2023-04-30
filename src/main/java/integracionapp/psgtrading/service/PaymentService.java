package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.*;
import integracionapp.psgtrading.repository.BalanceRepository;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final SymbolRepository symbolRepository;
    private final BalanceRepository balanceRepository;

    public void transferPayment(Income payment, User user) {
        Balance fiatBalance = balanceRepository.findBySymbolIsTokenFalseAndUser(user);
        if(fiatBalance == null){
            Symbol symbol = symbolRepository.findByIsTokenFalse();
            fiatBalance = new Balance(symbol, 0.0, user);
        }
        double newBalance = fiatBalance.getAmount() + payment.getAmount();
        fiatBalance.setAmount(newBalance);

        user.getHistoricalIncome().add(payment);
        userRepository.save(user);
        balanceRepository.save(fiatBalance);


    }

}
