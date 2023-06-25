package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.WebSocketConfig;
import integracionapp.psgtrading.dto.PaymenMethod;
import integracionapp.psgtrading.model.*;
import integracionapp.psgtrading.repository.BalanceRepository;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;
    @MockBean
    private WebSocketConfig webSocketConfig;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BalanceRepository balanceRepository;
    @MockBean
    private SymbolRepository symbolRepository;

    @Test
    void creditCardPayment_OK() {
        User mockUser = Mockito.mock(User.class);
        Balance mockBalance = Mockito.mock(Balance.class);

        Income payment = new Income(200.30, PaymenMethod.CREDIT_CARD, mockUser, "default");

        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(balanceRepository.findBySymbolIsTokenFalseAndUser(mockUser)).thenReturn(mockBalance);
        when(balanceRepository.save(mockBalance)).thenReturn(mockBalance);

        paymentService.transferPayment(payment, mockUser);

        verify(userRepository, times(1)).save(mockUser);
        verify(balanceRepository, times(1)).save(mockBalance);
        verify(mockBalance, times(1)).setAmount(200.30);
        Assertions.assertNotNull(mockUser.getBalances());
        Assertions.assertNotNull(balanceRepository.findBySymbolIsTokenFalseAndUser(mockUser));
    }

    @Test
    void firstCreditCardPayment() {
        User mockUser = Mockito.mock(User.class);

        Income payment = new Income(200.30, PaymenMethod.CREDIT_CARD, mockUser, "default");
        Symbol symbol = new Symbol("USD", false, "FIAT");

        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(balanceRepository.save(any(Balance.class))).thenReturn(null);
        when(symbolRepository.findByIsTokenFalse()).thenReturn(symbol);

        paymentService.transferPayment(payment, mockUser);

        verify(userRepository, times(1)).save(mockUser);
        verify(balanceRepository, times(1)).save(any(Balance.class));
        verify(symbolRepository, times(1)).findByIsTokenFalse();
    }

}
