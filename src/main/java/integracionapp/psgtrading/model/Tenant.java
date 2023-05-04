package integracionapp.psgtrading.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tenant", uniqueConstraints = @UniqueConstraint(columnNames = "domain"))
public class Tenant {

    @Id
    @Column(name = "tenant_id")
    private String tenantId;

    @NotNull
    @Column(name = "domain")
    private String domain;

}
