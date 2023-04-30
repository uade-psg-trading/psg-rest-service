package integracionapp.psgtrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TokenPrice {
    @Id
    @Column(name = "price_id")
    private String price_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "symbol", nullable = false)
    private Symbol symbol;

    @Positive
    @Column(name = "price")
    private Double price;

    @Column(name = "percent_change_24h")
    private Double percent_change_24h;

    @Column(name = "volume_change_24h")
    private Double volume_change_24h;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public TokenPrice(Symbol symbol, Double price, Double percent_change_24h, Double volume_change_24h) {
        this.symbol = symbol;
        this.price = price;
        this.percent_change_24h = percent_change_24h;
        this.volume_change_24h = volume_change_24h;
    }
}
