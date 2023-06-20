package integracionapp.psgtrading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import integracionapp.psgtrading.configuration.WebSocketConfig;
import integracionapp.psgtrading.dto.publisher.TransactionEvent;
import integracionapp.psgtrading.dto.publisher.TransactionEventData;
import integracionapp.psgtrading.dto.publisher.TransactionEventPayload;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CorePublisherService {
    private static final String ORIGIN = "/app/send/trading";
    private WebSocketConfig webSocketConfig;

    private final ObjectMapper objectMapper;

    public void sendMessage(TransactionEventData data, String action) throws JsonProcessingException {
        StompSession session;
        try {
            session = webSocketConfig.connectWebSocket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var payload = TransactionEventPayload.builder()
                .operation(action.equalsIgnoreCase("buy") ? "BUY_TOKEN" : "SELL_TOKEN")
                .data(data)
                .build();
        TransactionEvent transactionEvent = TransactionEvent.builder()
                .payload(payload)
                .from("TRADING")
                .timestamp(LocalDateTime.now())
                .build();

        session.send(ORIGIN, objectMapper.writeValueAsString(transactionEvent));
    }
}
