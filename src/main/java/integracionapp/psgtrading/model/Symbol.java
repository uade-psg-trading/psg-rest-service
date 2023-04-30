package integracionapp.psgtrading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Symbol {
    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name="istoken")
    private boolean isToken;

    public Symbol(String symbol, boolean isToken, String name) {
        this.symbol = symbol;
        this.isToken = isToken;
        this.name = name;
    }
}
