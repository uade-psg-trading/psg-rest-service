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
        uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "token" }) })
public class Balance {
    @Id
    private String token;
    private Double balance;

    @ManyToOne
    @JoinColumn(name ="user_id", nullable=false)
    private User user;
    private LocalDateTime updateTime;

}
