package integracionapp.psgtrading.model;

import integracionapp.psgtrading.dto.PaymenMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Income")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
    private long id;
    // 4 últimos digitos de la tarjeta de credito o transacicon id

    @Column(name = "tenant_id")
    private String tenantId;
    private double amount;
    private LocalDateTime historicalDate;
    private PaymenMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public Income(double amount, PaymenMethod paymentMethod, User user, String tenantId) {
        this.amount = amount;
        this.historicalDate = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.user = user;
        this.tenantId = tenantId;
    }
}
