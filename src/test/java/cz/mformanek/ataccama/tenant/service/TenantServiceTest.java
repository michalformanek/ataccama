package cz.mformanek.ataccama.tenant.service;

import cz.mformanek.ataccama.database.configuration.DataSourceManager;
import cz.mformanek.ataccama.tenant.exception.TenantNotFoundException;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @Mock
    DataSourceManager dataSourceManager;

    @Mock
    TenantRepository tenantRepository;

    @InjectMocks
    TenantService tenantService;

    @Test
    public void shouldGetAllTenants() {
        final List<Tenant> tenants = List.of(new Tenant(), new Tenant());
        when(tenantRepository.findAll()).thenReturn(tenants);
        final List<Tenant> result = tenantService.getTenants();
        assertSame(result, tenants);
    }

    @Test
    public void shouldFindTenantByName() {
        final Tenant tenant = new Tenant();
        when(tenantRepository.findById("name")).thenReturn(Optional.of(tenant));
        final Tenant result = tenantService.getTenantById("name");
        assertSame(result, tenant);
    }

    @Test
    public void shouldThrowExceptionWhenTenantDoesNotExist() {
        when(tenantRepository.findById("name")).thenReturn(Optional.empty());
        assertThrows(TenantNotFoundException.class, () -> tenantService.getTenantById("name"));
    }

    @Test
    public void shouldSaveNewTenant() {
        final var tenant = new Tenant();
        when(tenantRepository.save(any())).thenReturn(tenant);
        final var result = tenantService.saveTenant(tenant);
        verify(tenantRepository,times(1)).save(any());
        verify(dataSourceManager).addDataSource(any());
    }

    @Test
    public void shouldDeleteTenant() {
        tenantService.deleteTenant("tenant");
        verify(tenantRepository,times(1)).deleteById(any());
        verify(dataSourceManager).removeDataSource(any());
    }

    @Test
    public void shouldUpdateTenant() {
        when(tenantRepository.findById(eq("tenant"))).thenReturn(Optional.of(new Tenant("tenant", "name", "", "", "", "")));
        final Tenant updatedTenant = tenantService.updateTenant("tenant", new Tenant());
        verify(tenantRepository,times(1)).findById(any());
        verify(tenantRepository,times(1)).save(any());
        verify(dataSourceManager).addDataSource(any());
    }

}