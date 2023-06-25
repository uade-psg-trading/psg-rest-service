package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventPayload {
    public String operation;
    public UserEventData data;
}
