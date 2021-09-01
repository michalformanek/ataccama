package cz.mformanek.ataccama.tenant.service;

import cz.mformanek.ataccama.configuration.DataSourceConfiguration;
import cz.mformanek.ataccama.tenant.exception.TenantNotFoundException;
import cz.mformanek.ataccama.tenant.mapper.TenantMapper;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final DataSourceConfiguration dataSourceConfiguration;

    @PostConstruct
    public void preloadTenants() {
        tenantRepository.findAll().forEach(dataSourceConfiguration::addDataSource);
    }

    public List<Tenant> getTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(String id) {
        return tenantRepository.findById(id).orElseThrow(TenantNotFoundException::new);
    }

    public Tenant saveTenant(Tenant tenant) {
        final var saved = tenantRepository.save(tenant);
        dataSourceConfiguration.addDataSource(saved);
        return saved;
    }

    public Tenant updateTenant(String id, Tenant tenant) {
        final var original = tenantRepository.findById(id).orElseThrow(TenantNotFoundException::new);
        final var updated = TenantMapper.INSTANCE.update(original, tenant);
        final var saved = tenantRepository.save(updated);
        dataSourceConfiguration.addDataSource(saved);
        return saved;
    }

    public void deleteTenant(String id) {
        tenantRepository.deleteById(id);
        dataSourceConfiguration.removeDataSource(id);
    }
}
