package integracionapp.psgtrading.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebSocketClient {

    @Bean
    public StompSession getWebSocketClient() {
        // TODO: Borrar el try catch, hacer throws.
        // Otra opcion es agarrar y fijarse si falla conexion y retornar null.
        try {
            org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient();
            WebSocketStompClient stompClient = new WebSocketStompClient(client);
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());
            StompSessionHandler sessionHandler = new CoreMessageHandler();

            CompletableFuture<StompSession> completableFuture = stompClient.connectAsync("URL", sessionHandler);
            StompSession session = completableFuture.get(3, TimeUnit.SECONDS);
            if (!session.isConnected()) {
                System.out.println("Websocket wasn't connected");
            }

            return session;
        } catch (Exception ex) {
            return null;
        }
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
            return null;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {

        }
    }
}
