package cz.mformanek.ataccama.configuration;

import cz.mformanek.ataccama.tenant.exception.TenantNotFoundException;
import cz.mformanek.ataccama.tenant.model.Tenant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private final DataSourceContextHolder dataSourceContextHolder;
    private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();
    private final DataSource tenantDataSource;
    private AbstractRoutingDataSource multiTenantDataSource;

    @Bean
    @Primary
    public DataSource routingDatasource() {
        multiTenantDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return dataSourceContextHolder.currentDatasource();
            }
        };
        multiTenantDataSource.setTargetDataSources(tenantDataSources);
        multiTenantDataSource.setDefaultTargetDataSource(tenantDataSource);
        multiTenantDataSource.afterPropertiesSet();
        return multiTenantDataSource;
    }

    public void addDataSource(Tenant tenant) {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(String.format("jdbc:mysql://%s:%s/%s", tenant.getHostname(), tenant.getPort(), tenant.getDatabaseName()))
                .username(tenant.getUsername())
                .password(tenant.getPassword())
                .build();

        tenantDataSources.put(tenant.getName(), dataSource);
        multiTenantDataSource.afterPropertiesSet();
    }

    public void setCurrentDatasource(String name) {
        if (!tenantDataSources.containsKey(name)) {
            throw new TenantNotFoundException();
        }
        dataSourceContextHolder.setDatasource(name);
    }

    public void removeDataSource(String name) {
        throw new NotImplementedException();
    }
}

