package cz.mformanek.ataccama.database.configuration;

import cz.mformanek.ataccama.database.interceptor.DataSourceSwitchingInterceptor;
import cz.mformanek.ataccama.database.service.DatabaseService;
import cz.mformanek.ataccama.tenant.service.TenantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest()
class WebConfigurationTest {

    @MockBean
    TenantService tenantService;

    @MockBean
    DatabaseService databaseService;

    @MockBean
    DataSourceSwitchingInterceptor dataSourceSwitchingInterceptor;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldRegisterInterceptor() throws Exception {
        mockMvc.perform(get("/tenant/"));
        verify(dataSourceSwitchingInterceptor, times(0)).preHandle(any(),any(),any());
        mockMvc.perform(get("/database/"));
        verify(dataSourceSwitchingInterceptor, times(1)).preHandle(any(),any(),any());
    }
}