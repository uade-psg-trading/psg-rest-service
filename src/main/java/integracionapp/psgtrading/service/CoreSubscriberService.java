package integracionapp.psgtrading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.dto.publisher.IncomingMessage;
import integracionapp.psgtrading.dto.publisher.UserEvent;
import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.SymbolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CoreSubscriberService {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final BalanceService balanceService;
    private final SymbolRepository symbolRepository;

    public void readMessage(IncomingMessage payload) {
        try {
            UserEvent userEvent = objectMapper.readValue(payload.getContent(), UserEvent.class);

            Optional<User> user = userService.findByEmail(userEvent.getPayload().getData().getEmail());
            if (user.isPresent()) {
                final Double removeAmount = userEvent.getPayload().getData().getVoteQuantity();
                final String psgSymbol = "PSG";
                final Symbol symbol = symbolRepository.findBySymbol(psgSymbol);
                Balance balance = balanceService.getBalanceByUserEmailAndSymbol(user.get(), symbol);
                Double newAmount = balance.getAmount() - removeAmount;
                if (newAmount < 0) {
                    throw new RuntimeException("Not enough balance, current balance " + balance.getAmount() + " remove amount " + removeAmount + " new amount " + newAmount);
                }

                System.out.println("Old Amount " + balance.getAmount() + " New amount " + newAmount);
                balance.setAmount(newAmount);
                System.out.println("Balance updated " + balance.getAmount());
                balanceService.save(balance);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
