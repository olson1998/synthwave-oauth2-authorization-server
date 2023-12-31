package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.props.OAuth2PersistenceUnitProperties;
import com.github.olson1998.synthwave.support.jpa.config.AbstractPersistenceUnitConfig;
import com.github.olson1998.synthwave.support.migration.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableJpaRepositories(
        basePackages = "com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository",
        entityManagerFactoryRef = "oauth2PersistenceUnitEntityManagerFactory",
        transactionManagerRef = "oauth2PersistenceUnitPlatformTransactionManager"
)
@Configuration
@RequiredArgsConstructor
public class OAuth2PersistenceUnitConfig extends AbstractPersistenceUnitConfig {

    private final MigrationService migrationService;

    private final OAuth2PersistenceUnitProperties oAuth2PersistenceUnitProperties;

    @Bean
    public DataSource oauth2DataSource(){
        return initDataSource(migrationService, oAuth2PersistenceUnitProperties);
    }

    @Bean
    public HibernateJpaVendorAdapter oauth2PersistenceUnitJpaVendorAdapter(){
        return initHibernateJpaVendorAdapter(oAuth2PersistenceUnitProperties);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean oauth2PersistenceUnitEntityManagerFactory(DataSource oauth2DataSource,
                                                                             HibernateJpaVendorAdapter oauth2PersistenceUnitJpaVendorAdapter){
        return initEntityManagerFactory(
                oauth2DataSource,
                oauth2PersistenceUnitJpaVendorAdapter,
                oAuth2PersistenceUnitProperties
        );
    }

    @Bean
    public PlatformTransactionManager oauth2PersistenceUnitPlatformTransactionManager(LocalContainerEntityManagerFactoryBean oauth2PersistenceUnitEntityManagerFactory){
        return initPlatformTransactionManager(oauth2PersistenceUnitEntityManagerFactory);
    }
}
