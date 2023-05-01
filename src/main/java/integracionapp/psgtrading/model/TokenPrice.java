package integracionapp.psgtrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"price_id"})
public class TokenPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "price_id")
    private long price_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "symbol", nullable = false)
    private Symbol symbol;

    @Positive
    @Column(name = "price")
    private Double price;

    @Column(name = "volume_24h")
    private Double volume24h;

    @Column(name = "volume_change_24h")
    private Double volumeChange24h;

    @Column(name = "percent_change_1h")
    private Double percentChange1h;

    @Column(name = "percent_change_24h")
    private Double percentChange24h;

    @Column(name = "percent_change_7d")
    private Double percentChange7d;

    @Column(name = "percent_change_30d")
    private Double percentChange30d;

    @Column(name = "percent_change_60d")
    private Double percentChange60d;

    @Column(name = "market_cap")
    private Double marketCap;

    @Column(name = "market_cap_dominance")
    private Double marketCapDominance;

    @Column(name = "fully_diluted_market_cap")
    private Double fullyDilutedMarketCap;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

}
