package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionEventData {
    public Double quantity;
    public Double balance;
    public String symbol;
    public Double price;
    public String email;
    public Integer dni;
}
