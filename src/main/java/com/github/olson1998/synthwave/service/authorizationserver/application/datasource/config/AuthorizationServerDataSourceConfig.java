package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.config;

import com.github.olson1998.synthwave.support.jpa.config.AbstractPersistenceUnitConfig;
import com.github.olson1998.synthwave.support.jpa.props.PersistenceUnitProperties;
import com.github.olson1998.synthwave.support.migration.service.MigrationService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.config.AuthorizationServerDataSourceConfig.AUTHORIZATION_SERVER_ENTITY_MANAGER_FACTORY_BEAN;
import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.config.AuthorizationServerDataSourceConfig.AUTHORIZATION_SERVER_PLATFORM_TRANSACTION_MANAGER_BEAN;
import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.props.AuthorizationServerPersistenceUnitProperties.AUTHORIZATION_SERVER_PERSISTENCE_UNIT_PROPERTIES_BEAN;

@EnableJpaRepositories(
        entityManagerFactoryRef = AUTHORIZATION_SERVER_ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = AUTHORIZATION_SERVER_PLATFORM_TRANSACTION_MANAGER_BEAN,
        basePackages = {
                "com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.user",
                "com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oidc",
                "com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2"
        }
)
@Configuration
public class AuthorizationServerDataSourceConfig extends AbstractPersistenceUnitConfig {

    public static final String AUTHORIZATION_SERVER_DATASOURCE_BEAN = "authorizationServerDatasource";

    public static final String AUTHORIZATION_SERVER_HIBERNATE_VENDOR_ADAPTER_BEAN = "authorizationServerHibernateVendorAdapter";

    public static final String AUTHORIZATION_SERVER_ENTITY_MANAGER_FACTORY_BEAN = "authorizationServerEntityManagerFactory";

    public static final String AUTHORIZATION_SERVER_PLATFORM_TRANSACTION_MANAGER_BEAN = "authorizationServerPlatformTransactionManager";

    private final MigrationService migrationService;

    private final PersistenceUnitProperties persistenceUnitProperties;

    public AuthorizationServerDataSourceConfig(@NonNull MigrationService migrationService,
                                               @Qualifier(AUTHORIZATION_SERVER_PERSISTENCE_UNIT_PROPERTIES_BEAN) PersistenceUnitProperties persistenceUnitProperties) {
        this.migrationService = migrationService;
        this.persistenceUnitProperties = persistenceUnitProperties;
    }

    @Bean(AUTHORIZATION_SERVER_DATASOURCE_BEAN)
    public DataSource dataSource(){
        return initDataSource(migrationService, persistenceUnitProperties);
    }

    @Bean(AUTHORIZATION_SERVER_HIBERNATE_VENDOR_ADAPTER_BEAN)
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        return initHibernateJpaVendorAdapter(persistenceUnitProperties);
    }

    @Bean(AUTHORIZATION_SERVER_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(AUTHORIZATION_SERVER_DATASOURCE_BEAN) DataSource dataSource,
                                                                                         @Qualifier(AUTHORIZATION_SERVER_HIBERNATE_VENDOR_ADAPTER_BEAN) HibernateJpaVendorAdapter hibernateJpaVendorAdapter){
        return initEntityManagerFactory(dataSource, hibernateJpaVendorAdapter, persistenceUnitProperties);
    }

    @Bean(AUTHORIZATION_SERVER_PLATFORM_TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager platformTransactionManager(@Qualifier(AUTHORIZATION_SERVER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean em){
        return initPlatformTransactionManager(em);
    }
}
