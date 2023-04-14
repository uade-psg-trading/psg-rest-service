package integracionapp.psgtrading.service;

import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.*;
import integracionapp.psgtrading.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    public void creditCardPayment(Income payment, Card d) {

        User user = userRepository.findByEmailIgnoreCase(payment.getEmail()).orElseThrow();
       if ( validateCreditCard(d)) {
           if (user.getHistoricalIncome() == null) {
               user.setHistoricalIncome(new HashSet<>());
               user.getHistoricalIncome().add(payment);
           } else {
               user.getHistoricalIncome().add(payment);
           }

           userRepository.save(addIncomingPay(payment, user));
        } else {
           throw new CustomRuntimeException(ErrorCode.GENERAL_ERROR,"Failed to process payment");
       }
    }
    public void transferPayment(Income payment) {

        User user = userRepository.findByEmailIgnoreCase(payment.getEmail()).orElseThrow();
        user.getHistoricalIncome().add(payment);
        userRepository.save(addIncomingPay(payment, user));

    }

    private static User addIncomingPay(Income historicalIncome, User user) {
        if(user.getBalances() != null) {
            user.getBalances().stream()
                    .filter(b -> b.getSymbol().equals("ARS"))
                    .findAny().ifPresentOrElse(
                            b -> {
                                double aux = b.getAmount() + historicalIncome.getAmount();
                                b.setAmount(aux);
                                b.setUpdateTime(LocalDateTime.now());
                            },
                            () -> {
                                Balance balance = new Balance("ARS", historicalIncome.getAmount(), user,
                                        LocalDateTime.now());
                                user.getBalances().add(balance);
                            }
                    );
        } else {
            user.setBalances( new HashSet<>());
                Balance balance = new Balance("ARS", historicalIncome.getAmount(), user,
                    LocalDateTime.now());
                    user.getBalances().add(balance);
        }
        return user;
    }

    private boolean validateCreditCard(Card d) {
        //mock
        return d.getAccountNumber() != null;
    }

}
