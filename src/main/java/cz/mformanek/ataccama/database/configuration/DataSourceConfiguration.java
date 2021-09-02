package cz.mformanek.ataccama.database.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    @Getter
    private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();

    private final DataSourceContextHolder dataSourceContextHolder;

    @Bean
    @Primary
    public AbstractRoutingDataSource routingDatasource(DataSource tenantDataSource) {
        var multiTenantDataSource = new AbstractRoutingDataSource() {
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
}
