package integracionapp.psgtrading.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`User`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "external_identifier", unique = true)
    private long externalIdentifier;

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