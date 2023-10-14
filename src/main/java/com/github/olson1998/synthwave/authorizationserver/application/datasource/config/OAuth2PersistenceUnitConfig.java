package com.github.olson1998.synthwave.authorizationserver.application.datasource.config;

import com.github.olson1998.synthwave.datasourcemigration.service.MigrationService;
import com.github.olson1998.synthwave.jpa.config.AbstractPersistenceUnitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

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
