package integracionapp.psgtrading.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction")

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "token")
    private String token;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "operation")
    private String operation;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @ManyToOne
    @JoinColumn(name ="user_id", nullable=false)
    private User user;

    public Transaction() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transaction(String token, String quantity, Double price, Double balance, String operation, LocalDateTime transactionTime, User user) {
        this.token = token;
        this.quantity = quantity;
        this.price = price;
        this.balance = balance;
        this.operation = operation;
        this.transactionTime = transactionTime;
        this.user = user;
    }
}
