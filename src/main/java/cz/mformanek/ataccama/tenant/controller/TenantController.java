package cz.mformanek.ataccama.tenant.controller;

import cz.mformanek.ataccama.tenant.dto.TenantDto;
import cz.mformanek.ataccama.tenant.mapper.TenantMapper;
import cz.mformanek.ataccama.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    public List<TenantDto> getAllTenants() {
        final var tenants = tenantService.getTenants();
        return TenantMapper.INSTANCE.map(tenants);
    }

    @GetMapping("{id}")
    public TenantDto getAllTenants(@PathVariable String id) {
        final var tenant = tenantService.getTenantById(id);
        return TenantMapper.INSTANCE.map(tenant);

    }

    @PostMapping
    public TenantDto createTenant(@RequestBody @Valid TenantDto tenantDto) {
        final var tenant = TenantMapper.INSTANCE.map(tenantDto);
        final var savedTenant = tenantService.saveTenant(tenant);
        return TenantMapper.INSTANCE.map(savedTenant);
    }

    @PutMapping("{id}")
    public TenantDto updateTenant(@PathVariable String id, @RequestBody @Valid TenantDto tenantDto) {
        final var tenant = TenantMapper.INSTANCE.map(tenantDto);
        final var updatedTenant = tenantService.updateTenant(id, tenant);
        return TenantMapper.INSTANCE.map(updatedTenant);
    }


    @DeleteMapping("{id}")
    public void deleteTenant(@PathVariable String id) {
        tenantService.deleteTenant(id);
    }

}
