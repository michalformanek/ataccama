package cz.mformanek.ataccama.tenant.repository;

import cz.mformanek.ataccama.tenant.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {
}
