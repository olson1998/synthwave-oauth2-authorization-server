package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.config;

import com.github.olson1998.synthwave.support.jpa.config.*;
import com.github.olson1998.synthwave.support.migration.config.MigrationProperties;
import com.zaxxer.hikari.HikariConfig;
import jakarta.persistence.ValidationMode;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.authorizationserver.datasource")
public class OAuth2PersistenceUnitProperties implements PersistenceUnitProperties {

    @Getter
    private final HikariConfig hikari = new HikariConfig();

    private final OAuth2DataSourceMigrationProperties migration =
            new OAuth2DataSourceMigrationProperties();

    private final OAuth2PersistenceUnitVendorAdapterProperties vendor =
            new OAuth2PersistenceUnitVendorAdapterProperties();

    private final OAuth2PersistenceUnitEntityManagerProperties entityManager =
            new OAuth2PersistenceUnitEntityManagerProperties();

    @Override
    public MigrationProperties getMigration() {
        return migration;
    }

    @Override
    public VendorAdapterProps getVendorAdapter() {
        return vendor;
    }

    @Override
    public EntityManagerProps getEntityManager() {
        return entityManager;
    }

    @Data
    @Validated
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OAuth2DataSourceMigrationProperties implements MigrationProperties {

        private final String contextName = "OAuth2DataSource";

        private boolean autoMigrationEnabled = true;

        private String changelog = "classpath:db.changelog/_changlog-oauth2.xml";

        @Override
        public Map<String, String> getCustomVariables() {
            return Collections.emptyMap();
        }
    }

    @Setter
    @Validated
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OAuth2PersistenceUnitVendorAdapterProperties implements VendorAdapterProps{

        @Override
        public Optional<Boolean> isShowSql() {
            return Optional.empty();
        }

        @Override
        public Optional<Boolean> isPrepareConnection() {
            return Optional.empty();
        }
    }

    @Data
    @Validated
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OAuth2PersistenceUnitEntityManagerProperties implements EntityManagerProps{

        private String persistenceUnitName = "oauth2-datasource";

        private final String[] packagesToScan = {"com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity"};

        private final JpaDialectValue jpaDialectValue = JpaDialectValue.HIBERNATE;

        @Override
        public JpaDialectConfig getJpaDialect() {
            return jpaDialectValue;
        }

        @Override
        public Optional<ValidationMode> getOptionalValidationMode() {
            return Optional.empty();
        }
    }

}
