package integracionapp.psgtrading.model;

import integracionapp.psgtrading.dto.PaymenMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private String paymentId;
    private double amount;
    private LocalDate historicalDate;

    private String email;
    private PaymenMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public Income(String paymentId, double amount, LocalDate historicalDate, PaymenMethod paymentMethod, String email) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.historicalDate = historicalDate;
        this.paymentMethod = paymentMethod;
        this.email = email;
    }
}
