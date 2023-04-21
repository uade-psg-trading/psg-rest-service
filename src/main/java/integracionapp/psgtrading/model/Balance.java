package integracionapp.psgtrading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Balance",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "symbol" }) })
public class Balance {
    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name ="user_id", nullable=false)
    private User user;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
