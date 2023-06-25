package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserEvent {
    public Operation payload;

    @Data
    @AllArgsConstructor
    public class Operation {
        public String operation;
        public UserData data;

        @Data
        @AllArgsConstructor
        public class UserData {
            public String email;
            public Double VoteQuantity;
        }
    }
}
