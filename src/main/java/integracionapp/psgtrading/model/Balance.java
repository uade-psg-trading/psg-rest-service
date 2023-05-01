package integracionapp.psgtrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Balance",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "symbol"})})
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "balance_id")
    private long balance_id;

    @ManyToOne
    @JoinColumn(name = "symbol", nullable = false)
    private Symbol symbol;

    @PositiveOrZero
    @Column(name = "amount")
    private Double amount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Balance(Symbol symbol, Double amount, User user) {
        this.symbol = symbol;
        this.amount = amount;
        this.user = user;
    }
}
