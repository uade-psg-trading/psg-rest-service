package integracionapp.psgtrading.dto.response;

import integracionapp.psgtrading.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Yield {

    private Symbol symbol;
    private Double price;
    private Double percent_change_24h;
    private Double amount;
    private Double yield;
    private Double total;

}
