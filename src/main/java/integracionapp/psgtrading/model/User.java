package integracionapp.psgtrading.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
    private long id;

    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Wick")
    private String lastName;

    @Schema(example = "user@mail.com")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Schema(example = "my-password")
    private String password;

    @Schema(example = "9797595596")
    @Column(unique = true)
    private Integer dni;

    @Embedded
    private Location location;

    private String externalIdentifier = UUID.randomUUID().toString();

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private Set<Transaction> transactions;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private Set<Balance> balances;

    public User(String firstName, String lastName, String email, String password, Integer dni, Location location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.location = location;
    }
}