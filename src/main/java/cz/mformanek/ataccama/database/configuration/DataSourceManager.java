package cz.mformanek.ataccama.database.configuration;

import cz.mformanek.ataccama.tenant.exception.TenantNotFoundException;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceManager {

    private final DataSourceContextHolder dataSourceContextHolder;
    private final TenantRepository tenantRepository;
    private final DataSourceConfiguration dataSourceConfiguration;
    private final AbstractRoutingDataSource multiTenantDataSource;

    @PostConstruct
    public void preloadTenants() {
        tenantRepository.findAll().forEach(this::addDataSource);
    }

    public void removeDataSource(String name) {
        dataSourceConfiguration.getTenantDataSources().remove(name);
        multiTenantDataSource.afterPropertiesSet();
    }

    public void addDataSource(Tenant tenant) {
        DataSource newDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(String.format("jdbc:mysql://%s:%s/%s", tenant.getHostname(), tenant.getPort(), tenant.getDatabaseName()))
                .username(tenant.getUsername())
                .password(tenant.getPassword())
                .build();

        dataSourceConfiguration.getTenantDataSources().put(tenant.getName(), newDataSource);
        multiTenantDataSource.afterPropertiesSet();
        log.debug("Datasource {} added.", tenant.getName());
    }

    public void setCurrentDatasource(String name) {
        if (!dataSourceConfiguration.getTenantDataSources().containsKey(name)) {
            throw new TenantNotFoundException(name);
        }
        dataSourceContextHolder.setDatasource(name);
        log.debug("{} set as current datasource.", name);
    }

}
