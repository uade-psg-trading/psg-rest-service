package integracionapp.psgtrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`User`")
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Wick")
    private String lastName;

    @Schema(example = "user@mail.com")
    @NotNull
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Schema(example = "my-password")
    private String password;

    @Schema(example = "9797595596")
    @Positive
    private Integer dni;

    @Embedded
    private Location location;

    @JsonIgnore
    private String externalIdentifier = UUID.randomUUID().toString();

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private Set<Transaction> transactions;

    @JsonIgnore
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<Balance> balances;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Income> historicalIncome;

    public User(String firstName, String lastName, String email, String password, Integer dni, Location location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.location = location;
    }
}