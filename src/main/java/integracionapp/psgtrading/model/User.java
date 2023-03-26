package integracionapp.psgtrading.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "`User`")
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

    public User() {

    }

    public User(String firstName, String lastName, long externalIdentifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.externalIdentifier = externalIdentifier;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(long externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

}