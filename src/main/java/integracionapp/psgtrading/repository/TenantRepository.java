package integracionapp.psgtrading.repository;

import integracionapp.psgtrading.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Tenant findByTenantId(@Param("tenantId") String tenantId);
}


