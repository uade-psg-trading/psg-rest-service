package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class TransactionEvent {
    public TransactionEventPayload payload;
    public String from;
    public LocalDateTime timestamp;
}
