package cz.mformanek.ataccama.tenant.service;

import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public List<Tenant> getTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(String id) {
        return tenantRepository.getById(id); //TODO: Handle optional
    }
}
