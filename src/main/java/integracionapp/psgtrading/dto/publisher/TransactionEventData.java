package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionEventData {
    private Double quantity;
    private Double balance;
    private String symbol;
    private Double price;
    private String email;
    private Integer dni;
}
