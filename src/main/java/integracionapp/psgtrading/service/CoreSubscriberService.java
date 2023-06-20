package integracionapp.psgtrading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.dto.publisher.IncomingMessage;
import integracionapp.psgtrading.dto.publisher.TransactionEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoreSubscriberService {

    private final ObjectMapper objectMapper;

    public void readMessage(IncomingMessage payload) {
        try {
            var transactionEvent = objectMapper.readValue(payload.getContent(), TransactionEvent.class);
            System.out.println(transactionEvent.getPayload().getData().getSymbol());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
