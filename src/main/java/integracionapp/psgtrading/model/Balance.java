package integracionapp.psgtrading.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Balance",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "token" }) })
public class Balance {
    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "balance")
    private Double balance;

    @ManyToOne
    @JoinColumn(name ="user_id", nullable=false)
    private User user;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public Balance(){

    }

    public Balance(String token, Double balance, User user, LocalDateTime updateTime) {
        this.token = token;
        this.balance = balance;
        this.user = user;
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
