package cz.mformanek.ataccama.tenant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mformanek.ataccama.database.interceptor.DataSourceSwitchingInterceptor;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.service.TenantService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TenantController.class)
class TenantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DataSourceSwitchingInterceptor dataSourceSwitchingInterceptor;

    @MockBean
    TenantService tenantService;

    Tenant TENANT_1 = new Tenant("name-1", "host1", "123", "", "", "");
    Tenant TENANT_2 = new Tenant("name-2", "host2", "123", "", "", "");
    Tenant TENANT_3 = new Tenant("name-3", "host3", "123", "", "", "");

    @Test
    void shouldReturnTenants() throws Exception {
        when(tenantService.getTenants()).thenReturn(List.of(TENANT_1, TENANT_2, TENANT_3));
        mockMvc.perform(get("/tenants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].hostname", is("host3")));
    }

    @Test
    void shouldReturnSingleTenant() throws Exception {
        when(tenantService.getTenantById(anyString())).thenReturn(TENANT_3);
        mockMvc.perform(get("/tenants/name-3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hostname", is("host3")));
    }

    @Test
    public void shouldCreateNewTenant() throws Exception {
        Tenant tenant = new Tenant("ten", "", "", "", "", "");
        when(tenantService.saveTenant(tenant)).thenReturn(tenant);

        MockHttpServletRequestBuilder mockRequest = post("/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tenant));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(tenant.getName())));
    }


    @Test
    public void shouldUpdateTenant() throws Exception {
        Tenant tenant = new Tenant("ten", "", "", "", "", "");
        when(tenantService.updateTenant(eq("ten"), eq(tenant))).thenReturn(tenant);

        MockHttpServletRequestBuilder mockRequest = put("/tenants/ten")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tenant));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
        verify(tenantService, times(1)).updateTenant(eq("ten"), ArgumentMatchers.any(Tenant.class));
    }

    @Test
    public void shouldDeleteTenantById() throws Exception {
        mockMvc.perform(delete("/tenants/tenant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(tenantService, times(1)).deleteTenant(eq("tenant"));
    }


}