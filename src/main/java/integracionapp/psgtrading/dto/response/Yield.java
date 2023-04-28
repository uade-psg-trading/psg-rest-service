package integracionapp.psgtrading.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Yield {

    private String symbol;
    private Double price;
    private Double percent_change_24h;
    private Double amount;
    private Double yield;
    private Double total;

}
