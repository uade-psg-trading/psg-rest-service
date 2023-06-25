package integracionapp.psgtrading.configuration;

import integracionapp.psgtrading.dto.publisher.IncomingMessage;
import integracionapp.psgtrading.service.CoreSubscriberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
public class WebSocketConfig {

    @Value("${core.queue.url}")
    private String tradingURL;
    @Value("${core.queue.users}")
    private String usersUrl;
    private StompSession tradingSession;
    private StompSession userSession;

    private final CoreSubscriberService coreSubscriberService;

    public WebSocketConfig(CoreSubscriberService coreSubscriberService) {
        this.coreSubscriberService = coreSubscriberService;
    }

    public StompSession connectToTrading() throws Exception {
        if (tradingSession != null && tradingSession.isConnected()) {
            return tradingSession;
        }

        System.out.println("Connecting to " + tradingURL);
        StompSessionHandler sessionHandler = new CoreMessageHandler();
        tradingSession = connectToWs(tradingURL, sessionHandler);
        if (!tradingSession.isConnected()) {
            return null;
        }

        System.out.println("Connected");
        return tradingSession;
    }

    public void listenToUsers() throws ExecutionException, InterruptedException, TimeoutException {
        if (userSession != null && userSession.isConnected()) {
            return;
        }

        System.out.println("Connecting to " + usersUrl);
        StompSessionHandler sessionHandler = new CoreMessageHandler();
        userSession = connectToWs(tradingURL, sessionHandler);
        if (!userSession.isConnected()) {
            System.out.println("Couldn't connect");
            return;
        }

        userSession.subscribe("/topic/users", sessionHandler);
        System.out.println("Connected");
    }

    @Scheduled(cron = "0 * * * * *") // ejecuta cada minuto
    @EventListener(ApplicationReadyEvent.class)
    public void listenToQueues() {
        if (userSession == null || !userSession.isConnected()) {
            try {
                listenToUsers();
            } catch (Exception e) {

            }
        }
    }

    private StompSession connectToWs(String url, StompSessionHandler sessionHandler) throws ExecutionException, InterruptedException, TimeoutException {
        StompSession result;
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        CompletableFuture<StompSession> completableFuture = stompClient.connectAsync(url, sessionHandler);
        result = completableFuture.get(3, TimeUnit.SECONDS);
        return result;
    }

    public class CoreMessageHandler implements StompSessionHandler {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            System.out.println("Error " + exception.getMessage());
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            System.out.println("Error " + exception.getMessage());
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
