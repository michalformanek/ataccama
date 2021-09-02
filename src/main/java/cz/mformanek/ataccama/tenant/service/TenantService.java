package cz.mformanek.ataccama.tenant.service;

import cz.mformanek.ataccama.database.configuration.DataSourceManager;
import cz.mformanek.ataccama.tenant.exception.TenantNotFoundException;
import cz.mformanek.ataccama.tenant.mapper.TenantMapper;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final DataSourceManager dataSourceManager;


    public List<Tenant> getTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(String id) {
        return tenantRepository.findById(id).orElseThrow(() -> new TenantNotFoundException(id));
    }

    public Tenant saveTenant(Tenant tenant) {
        log.info("Saving new tenant {}", tenant.getName());
        final var saved = tenantRepository.save(tenant);
        dataSourceManager.addDataSource(saved);
        return saved;
    }

    public Tenant updateTenant(String id, Tenant tenant) {
        log.info("Updating tenant {}", id);
        final var original = tenantRepository.findById(id).orElseThrow(() -> new TenantNotFoundException(id));
        final var updated = TenantMapper.INSTANCE.update(original, tenant);
        final var saved = tenantRepository.save(updated);
        dataSourceManager.addDataSource(saved);
        return saved;
    }

    public void deleteTenant(String id) {
        log.info("Deleting tenant {}", id);
        tenantRepository.deleteById(id);
        dataSourceManager.removeDataSource(id);
    }
}
