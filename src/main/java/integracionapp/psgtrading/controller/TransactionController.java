package integracionapp.psgtrading.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.dto.TransactionDTO;
import integracionapp.psgtrading.dto.publisher.TransactionEventData;
import integracionapp.psgtrading.model.*;
import integracionapp.psgtrading.repository.*;
import integracionapp.psgtrading.service.CorePublisherService;
import integracionapp.psgtrading.service.JwtService;
import integracionapp.psgtrading.service.NewStockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final TokenPriceRepository tokenPriceRepository;
    private final CorePublisherService corePublisherService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, UserRepository userRepository,
                                 JwtService jwtService, SymbolRepository symbolRepository,
                                 BalanceRepository balanceRepository, NewStockService newStockService,
                                 TokenPriceRepository tokenPriceRepository, CorePublisherService corePublisherService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.symbolRepository = symbolRepository;
        this.balanceRepository = balanceRepository;
        this.newStockService = newStockService;
        this.tokenPriceRepository = tokenPriceRepository;
        this.corePublisherService = corePublisherService;
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

    @PostMapping("/{transactionType}")
    public ResponseEntity<GenericDTO<Transaction>> createTransaction(@PathVariable String transactionType,
                                                                     @Valid @RequestBody TransactionDTO transactionDTO,
                                                                     @RequestHeader("Authorization") String jwt) {
        try {
            JWTObjectDTO jwtObjectDTO = jwtService.decodeJWT(jwt);
            User user = userRepository.findById(jwtObjectDTO.getUserId())
                    .orElseThrow(EntityNotFoundException::new);
            String token = transactionDTO.getToken();
            Double quantity = transactionDTO.getQuantity();
            Symbol symbol = symbolRepository.findBySymbol(token);
            if (symbol == null) {
                return ResponseEntity.status(400).body(GenericDTO.error("Token not found."));
            }
            Balance balance = balanceRepository.findBySymbolAndUser(symbol, user);
            Balance fiatBalance = balanceRepository.findBySymbolIsTokenFalseAndUser(user);

            if(fiatBalance == null){
                return ResponseEntity.status(400).body(GenericDTO.error("User has no fiat balance yet. " +
                        "Try to ingress money first." + token));
            }

            Double price ;
            Double newAmount;
            Double newFiatBalance;

            try{
                price = newStockService.getCoinPrice(token).getPrice();
            }
            catch (WebClientRequestException e){
                TokenPrice tokenPrice = tokenPriceRepository.findFirstBySymbolOrderByUpdateTimeDesc(symbol);
                if(tokenPrice == null){
                    return ResponseEntity.status(400).body(GenericDTO.error("No prices found yet for " + token));
                }
                price = tokenPrice.getPrice();
            }

            if (balance == null) {
                balance = new Balance(symbol, 0.0, user);
            }

            String action;
            if ("buy".equalsIgnoreCase(transactionType)) {
                if (fiatBalance.getAmount() < price * quantity) {
                    return ResponseEntity.status(400).body(GenericDTO.error("Insufficient money available. " +
                            "You have " + fiatBalance.getAmount() + " available."));
                }
                newAmount = balance.getAmount() + quantity;
                newFiatBalance = fiatBalance.getAmount() - price * quantity;
                action = "BUY";
            } else if ("sell".equalsIgnoreCase(transactionType)) {
                if (balance.getAmount() < quantity) {
                    return ResponseEntity.status(400).body(GenericDTO.error("Insufficient amount of tokens available." +
                            "You have " + balance.getAmount() + " " + token + " available."));
                }
                newAmount = balance.getAmount() - quantity;
                newFiatBalance = fiatBalance.getAmount() + price * quantity;
                action = "SELL";
            } else {
                return ResponseEntity.status(400).body(GenericDTO.error("Invalid transaction type."));
            }

            balance.setAmount(newAmount);
            fiatBalance.setAmount(newFiatBalance);
            Transaction transaction = transactionRepository
                    .save(new Transaction(symbol, quantity, price, balance.getAmount(), action, user));
            balanceRepository.save(balance);
            balanceRepository.save(fiatBalance);
            corePublisherService.sendMessage(
                    TransactionEventData.builder()
                            .quantity(quantity)
                            .balance(balance.getAmount())
                            .symbol(symbol.getSymbol())
                            .price(price)
                            .email(user.getEmail())
                            .dni(user.getDni())
                            .build(), action);
            logger.info("Transaction complete: {} {} {} tokens at {} for user {}.", action, quantity,
                    symbol.getSymbol(), price, user.getEmail());
            return new ResponseEntity<>(GenericDTO.success(transaction), HttpStatus.CREATED);
        } catch (EntityNotFoundException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", exc);
        } catch (JsonProcessingException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error", exc);
        }
    }

}
