package integracionapp.psgtrading.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoreService {
    private static final String ORIGIN = "/app/send/trading";
    private StompSession coreWebSocketClient;

    public void sendMessage(String message){
        coreWebSocketClient.send(ORIGIN, message);
    }

}
