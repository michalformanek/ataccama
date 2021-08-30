package cz.mformanek.ataccama.tenant.controller;

import cz.mformanek.ataccama.tenant.dto.TenantDto;
import cz.mformanek.ataccama.tenant.mapper.TenantMapper;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    public List<TenantDto> getAllTenants() {
        final List<Tenant> tenants = tenantService.getTenants();
        return TenantMapper.INSTANCE.map(tenants);
    }

    @GetMapping("{id}")
    public TenantDto getAllTenants(@PathVariable String id) {
        final Tenant tenant = tenantService.getTenantById(id);
        return TenantMapper.INSTANCE.map(tenant);

    }

}
