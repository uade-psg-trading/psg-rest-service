package integracionapp.psgtrading.model;
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
//todo: preguntar si es necesario esto
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "external_identifier", unique = true)
    private long externalIdentifier;

    @OneToMany(mappedBy="user")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy="user")
    private Set<Balance> balances;

    public User(String firstName, String lastName, long externalIdentifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.externalIdentifier = externalIdentifier;
    }
}