package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TransactionEvent {
    private TransactionEventPayload payload;
    private String from;
    private LocalDateTime timestamp;
}
