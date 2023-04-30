package integracionapp.psgtrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
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
@Table(name = "Transaction")

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "token", nullable = false)
    private Symbol token;

    @Column(name = "quantity")
    @Positive
    private Double quantity;

    @Positive
    @Column(name = "price")
    private Double price;

    @PositiveOrZero
    @Column(name = "balance")
    private Double balance;

    @Column(name = "operation")
    private String operation;

    @Column(name = "transaction_time")
    @UpdateTimestamp
    private LocalDateTime transactionTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Transaction(Symbol token, Double quantity, Double price, Double balance, String operation, User user) {
        this.token = token;
        this.quantity = quantity;
        this.price = price;
        this.balance = balance;
        this.operation = operation;
        this.user = user;
    }
}
