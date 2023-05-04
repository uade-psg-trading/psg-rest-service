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
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "alert_id")
    private long alertId;

    @ManyToOne
    @JoinColumn(name = "symbol", nullable = false)
    private Symbol symbol;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PositiveOrZero
    @Column(name = "amount")
    private Double amount;


    @Column(name = "operator")
    @Enumerated(EnumType.STRING)
    private Operator operator;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public enum Operator {
        LOWER,
        GREATER
    }
}
