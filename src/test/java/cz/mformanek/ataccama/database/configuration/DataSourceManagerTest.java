package cz.mformanek.ataccama.database.configuration;

import cz.mformanek.ataccama.tenant.exception.TenantNotFoundException;
import cz.mformanek.ataccama.tenant.model.Tenant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.util.Assert.isNull;
import static org.springframework.util.Assert.notNull;

@ExtendWith(MockitoExtension.class)
class DataSourceManagerTest {

    @Mock
    DataSourceContextHolder dataSourceContextHolder;

    @Mock
    AbstractRoutingDataSource dataSource;

    @Mock
    DataSourceConfiguration dataSourceConfiguration;

    @InjectMocks
    DataSourceManager dataSourceManager;

    Map<Object, Object> datasources = new HashMap<>();

    @BeforeEach
    void setup() {
        when(dataSourceConfiguration.getTenantDataSources()).thenReturn(datasources);
    }

    @Test
    public void shouldRemoveDataSource() {
        datasources.put("test", DataSourceBuilder.create().build());
        dataSourceManager.removeDataSource("test");
        verify(dataSource, times(1)).afterPropertiesSet();
        isNull(datasources.get("test"), "DataSource should be removed");
    }

    @Test
    public void shouldAddTenantDataSource() {
        dataSourceManager.addDataSource(new Tenant("name", "hostname", "1234", "database", "user", "pass"));
        verify(dataSource, times(1)).afterPropertiesSet();
        final DataSource datasource = (DataSource) datasources.get("name");
        notNull(datasource, "DataSource should exist under its name");
    }

    @Test
    public void shouldSwitchDatasource() {
        dataSourceManager.addDataSource(new Tenant("name", "hostname", "1234", "database", "user", "pass"));
        dataSourceManager.setCurrentDatasource("name");
        verify(dataSourceContextHolder, times(1)).setDatasource(anyString());
    }

    @Test
    public void switchingShouldThrowExceptionWhenTenantDoesNotExist() {
        assertThrows(TenantNotFoundException.class, () -> dataSourceManager.setCurrentDatasource("name"));
    }

}

