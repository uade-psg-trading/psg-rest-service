package integracionapp.psgtrading.service;

import integracionapp.psgtrading.controller.TransactionController;
import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.TransactionDTO;
import integracionapp.psgtrading.dto.coins.response.CoinDTO;
import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.Transaction;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.TransactionRepository;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BalanceRepository balanceRepository;
    @MockBean
    private SymbolRepository symbolRepository;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private NewStockService newStockService;
    @MockBean
    private TransactionRepository transactionRepository;

    private Map<String, Object> getMocks(String token, Double quantity, Double balanceAmount, Double fiatBalanceAmount, Double coinPrice) {
        User mockUser = Mockito.mock(User.class);
        Symbol mockSymbol = Mockito.mock(Symbol.class);
        Balance mockBalance = Mockito.mock(Balance.class);
        Balance mockFiatBalance = Mockito.mock(Balance.class);
        Transaction mockTransaction = Mockito.mock(Transaction.class);
        TransactionDTO mockTransactionDTO = Mockito.mock(TransactionDTO.class);
        JWTObjectDTO mockJwtObjectDTO = Mockito.mock(JWTObjectDTO.class);
        CoinDTO coinDTO = Mockito.mock(CoinDTO.class);

        // Set up mock object behavior
        when(jwtService.decodeJWT(anyString())).thenReturn(mockJwtObjectDTO);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(symbolRepository.findBySymbol(anyString())).thenReturn(mockSymbol);
        when(balanceRepository.findBySymbolAndUser(any(Symbol.class), any(User.class))).thenReturn(mockBalance);
        when(balanceRepository.findBySymbolIsTokenFalseAndUser(any(User.class))).thenReturn(mockFiatBalance);
        when(newStockService.getCoinPrice(anyString())).thenReturn(coinDTO);
        when(coinDTO.getPrice()).thenReturn(coinPrice);
        when(mockTransactionDTO.getToken()).thenReturn(token);
        when(mockTransactionDTO.getQuantity()).thenReturn(quantity);
        when(mockBalance.getAmount()).thenReturn(balanceAmount);
        when(mockFiatBalance.getAmount()).thenReturn(fiatBalanceAmount);
        when(mockUser.getId()).thenReturn(1L);
        when(balanceRepository.save(mockBalance)).thenReturn(mockBalance);
        when(balanceRepository.save(mockFiatBalance)).thenReturn(mockFiatBalance);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

        Map<String, Object> mocks = new HashMap<>();
        mocks.put("mockUser", mockUser);
        mocks.put("mockSymbol", mockSymbol);
        mocks.put("mockBalance", mockBalance);
        mocks.put("mockFiatBalance", mockFiatBalance);
        mocks.put("mockTransaction", mockTransaction);
        mocks.put("mockTransactionDTO", mockTransactionDTO);
        mocks.put("mockJwtObjectDTO", mockJwtObjectDTO);
        mocks.put("coinDTO", coinDTO);
        return mocks;
    }


    @Test
    void testCreateBuyTransaction() {
        TransactionController transactionController = new TransactionController(transactionRepository,
                userRepository, jwtService, symbolRepository, balanceRepository, newStockService);

        // Get the mock objects and their behaviors
        Map<String, Object> mocks = getMocks("PSG", 1.0, 2.0, 10000.0, 99.2);

        // Extract the mock objects from the map
        Transaction mockTransaction = (Transaction) mocks.get("mockTransaction");
        TransactionDTO mockTransactionDTO = (TransactionDTO) mocks.get("mockTransactionDTO");

        // Call the function
        ResponseEntity<GenericDTO<Transaction>> responseEntity = transactionController.createBuyTransaction(mockTransactionDTO, "Bearer token");

        // Check that the response is successful
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Check that the transaction was saved
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        // Check that the balances were updated
        verify(balanceRepository, times(2)).save(any(Balance.class));

        // Check that the correct transaction was returned
        assertEquals(mockTransaction, responseEntity.getBody().getData());
    }

    @Test
    void testNotEnoughMoneyTransaction() {
        TransactionController transactionController = new TransactionController(transactionRepository,
                userRepository, jwtService, symbolRepository, balanceRepository, newStockService);

        Double currentBalance = 12.0;

        // Get the mock objects and their behaviors
        Map<String, Object> mocks = getMocks("PSG", 10.0, 2.0, currentBalance, 99.2);

        TransactionDTO mockTransactionDTO = (TransactionDTO) mocks.get("mockTransactionDTO");

        // Call the function
        ResponseEntity<GenericDTO<Transaction>> responseEntity = transactionController.createBuyTransaction(mockTransactionDTO, "Bearer token");

        // Check that the response is successful
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Check that the transaction was saved
        verify(transactionRepository, times(0)).save(any(Transaction.class));

        // Check that the balances were updated
        verify(balanceRepository, times(0)).save(any(Balance.class));

        assertEquals("Insufficient money available. You have " + currentBalance + " available.", responseEntity.getBody().getMessage());

    }

    @Test
    void testCreateSellTransaction() {
        TransactionController transactionController = new TransactionController(transactionRepository,
                userRepository, jwtService, symbolRepository, balanceRepository, newStockService);

        // Get the mock objects and their behaviors
        Map<String, Object> mocks = getMocks("PSG", 1.0, 2.0, 10000.0, 99.2);

        // Extract the mock objects from the map
        Transaction mockTransaction = (Transaction) mocks.get("mockTransaction");
        TransactionDTO mockTransactionDTO = (TransactionDTO) mocks.get("mockTransactionDTO");

        // Call the function
        ResponseEntity<GenericDTO<Transaction>> responseEntity = transactionController.createSellTransaction(mockTransactionDTO, "Bearer token");

        // Check that the response is successful
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Check that the transaction was saved
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        // Check that the balances were updated
        verify(balanceRepository, times(2)).save(any(Balance.class));

        // Check that the correct transaction was returned
        assertEquals(mockTransaction, responseEntity.getBody().getData());
    }

    @Test
    void testNotEnoughTokensToSellTransaction() {
        TransactionController transactionController = new TransactionController(transactionRepository,
                userRepository, jwtService, symbolRepository, balanceRepository, newStockService);

        String token = "PSG";
        Double balance = 2.0;

        // Get the mock objects and their behaviors
        Map<String, Object> mocks = getMocks(token, 10.0, balance, 10.0, 99.2);

        TransactionDTO mockTransactionDTO = (TransactionDTO) mocks.get("mockTransactionDTO");

        // Call the function
        ResponseEntity<GenericDTO<Transaction>> responseEntity = transactionController.createSellTransaction(mockTransactionDTO, "Bearer token");

        // Check that the response is successful
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Check that the transaction was saved
        verify(transactionRepository, times(0)).save(any(Transaction.class));

        // Check that the balances were updated
        verify(balanceRepository, times(0)).save(any(Balance.class));

        assertEquals("Insufficient amount of tokens available.You have " + balance + " " + token + " available.", responseEntity.getBody().getMessage());

    }


}
