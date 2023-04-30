package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.TransactionDTO;
import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.Transaction;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.BalanceRepository;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.TransactionRepository;
import integracionapp.psgtrading.repository.UserRepository;
import integracionapp.psgtrading.service.JwtService;
import integracionapp.psgtrading.service.NewStockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final SymbolRepository symbolRepository;
    private final BalanceRepository balanceRepository;

    private final JwtService jwtService;
    private final NewStockService newStockService;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository,
                                 JwtService jwtService, SymbolRepository symbolRepository,
                                 BalanceRepository balanceRepository, NewStockService newStockService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.symbolRepository = symbolRepository;
        this.balanceRepository = balanceRepository;
        this.newStockService = newStockService;

    }

    @GetMapping("")
    public ResponseEntity<GenericDTO<List<Transaction>>> getUserTransactions(@RequestHeader("Authorization") String jwt) {
        try {
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);

            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
            List<Transaction> transactions = new ArrayList<>(transactionRepository.findByUser(user));
            return new ResponseEntity<>(GenericDTO.success(transactions), HttpStatus.OK);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<GenericDTO<Transaction>> createBuyTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
                                                                        @RequestHeader("Authorization") String jwt) {
        try {
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);
            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
            String token = transactionDTO.getToken();
            Double quantity = transactionDTO.getQuantity();
            Symbol symbol = symbolRepository.findBySymbol(token);
            if (symbol == null) {
                return ResponseEntity.status(400).body(GenericDTO.error("Token not found."));
            }
            Balance balance = balanceRepository.findBySymbolAndUser(symbol, user);
            Balance fiatBalance = balanceRepository.findBySymbolIsTokenFalseAndUser(user);
            Double price = newStockService.getCoinPrice(token).getPrice();
            if (balance == null) {
                balance = new Balance(symbol, 0.0, user);
            }
            if (fiatBalance.getAmount() < price * quantity) {
                return ResponseEntity.status(400).body(GenericDTO.error("Insufficient money available. " +
                        "You have " + fiatBalance.getAmount() + " available."));
            }
            Double newAmount = balance.getAmount() + quantity;
            Double newFiatBalance = fiatBalance.getAmount() - price * quantity;

            balance.setAmount(newAmount);
            fiatBalance.setAmount(newFiatBalance);
            Transaction transaction = transactionRepository
                    .save(new Transaction(symbol, quantity, price, balance.getAmount(), "BUY", user));
            balanceRepository.save(balance);
            balanceRepository.save(fiatBalance);
            return new ResponseEntity<>(GenericDTO.success(transaction), HttpStatus.CREATED);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }

    }

    @PostMapping("/sell")
    public ResponseEntity<GenericDTO<Transaction>> createSellTransaction(@Valid  @RequestBody TransactionDTO transactionDTO,
                                                                         @RequestHeader("Authorization") String jwt) {
        try {
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);
            User user = userRepository.findById(jwtObjectDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
            String token = transactionDTO.getToken();
            Double quantity = transactionDTO.getQuantity();
            Symbol symbol = symbolRepository.findBySymbol(token);
            if (symbol == null) {
                return ResponseEntity.status(400).body(GenericDTO.error("Token not found."));
            }
            Balance balance = balanceRepository.findBySymbolAndUser(symbol, user);
            Balance fiatBalance = balanceRepository.findBySymbolIsTokenFalseAndUser(user);
            Double price = newStockService.getCoinPrice(token).getPrice();
            if (balance == null) {
                balance = new Balance(symbol, 0.0, user);
            }
            if (balance.getAmount() < quantity) {
                return ResponseEntity.status(400).body(GenericDTO.error("Insufficient amount of tokens available." +
                        "You have " + balance.getAmount() + " " + token + " available."));
            }
            Double newAmount = balance.getAmount() - quantity;
            Double newFiatBalance = fiatBalance.getAmount() + price * quantity;

            balance.setAmount(newAmount);
            fiatBalance.setAmount(newFiatBalance);
            Transaction transaction = transactionRepository
                    .save(new Transaction(symbol, quantity, price, balance.getAmount(), "SELL", user));
            balanceRepository.save(balance);
            balanceRepository.save(fiatBalance);
            return new ResponseEntity<>(GenericDTO.success(transaction), HttpStatus.CREATED);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        }

    }
}
