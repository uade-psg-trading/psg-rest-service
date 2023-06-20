package integracionapp.psgtrading.configuration;

import integracionapp.psgtrading.dto.publisher.IncomingMessage;
import integracionapp.psgtrading.service.CoreSubscriberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebSocketConfig {

    @Value("${core.queue.url}")
    private String url;
    private StompSession currentSession;

    private final CoreSubscriberService coreSubscriberService;

    public WebSocketConfig(CoreSubscriberService coreSubscriberService) {
        this.coreSubscriberService = coreSubscriberService;
    }

    public StompSession connectWebSocket() throws Exception {
        if (currentSession != null && currentSession.isConnected()) {
            return currentSession;
        }

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new CoreMessageHandler();

        CompletableFuture<StompSession> completableFuture = stompClient.connectAsync(url, sessionHandler);
        currentSession = completableFuture.get(3, TimeUnit.SECONDS);
        if (!currentSession.isConnected()) {
            return null;
        }
        currentSession.subscribe("/topic/trading", sessionHandler);
        return currentSession;
    }

    public class CoreMessageHandler implements StompSessionHandler {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {

        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return IncomingMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            IncomingMessage message = (IncomingMessage) payload;
            coreSubscriberService.readMessage(message);
        }
    }
}
