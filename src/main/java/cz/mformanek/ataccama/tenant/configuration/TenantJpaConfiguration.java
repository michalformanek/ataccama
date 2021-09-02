package cz.mformanek.ataccama.tenant.configuration;

import cz.mformanek.ataccama.tenant.repository.TenantRepository;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = TenantRepository.class,
        entityManagerFactoryRef = "tenantEntityManager",
        transactionManagerRef = "tenantTransactionManager"
)
public class TenantJpaConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean tenantEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter());
        entityManagerFactoryBean.setDataSource(tenantDataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("cz.mformanek.ataccama.tenant");
        entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());

        return entityManagerFactoryBean;
    }

    private Properties jpaHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        properties.put("hibernate.jdbc.lob.non_contextual_creation", false);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        return properties;
    }


    private HibernateJpaVendorAdapter vendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        return vendorAdapter;
    }

    @Bean
    public PlatformTransactionManager tenantTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManager().getObject());
        return transactionManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource tenantDataSource() {
        return DataSourceBuilder.create().build();
    }


}
