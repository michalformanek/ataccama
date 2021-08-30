package cz.mformanek.ataccama.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private final DataSourceContextHolder dataSourceContextHolder;

    private final DataSource primaryDataSource;
    private final DataSource secondaryDataSource;

    @Primary
    @Bean
    public DataSource routingDatasource() {
        var multiTenantDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return dataSourceContextHolder.currentDatasource();
            }
        };
        multiTenantDataSource.setTargetDataSources(Map.of("first", primaryDataSource, "second", secondaryDataSource));
        multiTenantDataSource.setDefaultTargetDataSource(primaryDataSource);
        return multiTenantDataSource;
    }
}

