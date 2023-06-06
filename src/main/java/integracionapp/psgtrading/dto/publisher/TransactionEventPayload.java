package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionEventPayload {
    private String operation;
    private TransactionEventData data;
}
