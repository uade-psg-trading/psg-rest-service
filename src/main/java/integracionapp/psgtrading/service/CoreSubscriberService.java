package integracionapp.psgtrading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.dto.publisher.IncomingMessage;
import integracionapp.psgtrading.dto.publisher.TransactionEvent;
import integracionapp.psgtrading.dto.publisher.UserEvent;
import integracionapp.psgtrading.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CoreSubscriberService {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public void readMessage(IncomingMessage payload) {
        try {
            var userEvent = objectMapper.readValue(payload.getContent(), UserEvent.class);
            System.out.println("Test " + payload);

            Optional<User> user = userService.findByEmail(userEvent.getPayload().getData().getEmail());
            if (user.isPresent()) {
                final Integer removeAmount = userEvent.getPayload().getData().getVoteQuantity();
                final String psgSymbol = "PSG";
                User currentUser = user.get();
                currentUser.getBalances().stream().filter(balance -> balance.getSymbol().getSymbol().equalsIgnoreCase(psgSymbol)).findFirst().ifPresent(balance -> {
                    Double newAmount = (double) balance.getAmount() - removeAmount;
                    if (newAmount < 0) {
                        throw new RuntimeException("Not enough balance, current balance " + balance.getAmount() + " remove amount " + removeAmount + " new amount " + newAmount);
                    }

                    currentUser.getBalances().remove(balance);
                    balance.setAmount(newAmount);
                    currentUser.getBalances().add(balance);
                    userService.save(currentUser);
                });
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
