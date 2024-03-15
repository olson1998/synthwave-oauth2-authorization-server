package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.props;

import com.github.olson1998.synthwave.support.jpa.config.JpaDialectConfig;
import com.github.olson1998.synthwave.support.jpa.props.EntityManagerProps;
import com.github.olson1998.synthwave.support.jpa.props.PersistenceUnitProperties;
import com.github.olson1998.synthwave.support.jpa.props.VendorAdapterProps;
import com.github.olson1998.synthwave.support.migration.config.MigrationProperties;
import com.zaxxer.hikari.HikariConfig;
import jakarta.persistence.ValidationMode;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.application.datasource.props.AuthorizationServerPersistenceUnitProperties.AUTHORIZATION_SERVER_PERSISTENCE_UNIT_PROPERTIES_BEAN;

@Data
@Validated
@Configuration(AUTHORIZATION_SERVER_PERSISTENCE_UNIT_PROPERTIES_BEAN)
@ConfigurationProperties(prefix = "synthwave.service.authorizationserver.datasource")
public class AuthorizationServerPersistenceUnitProperties implements PersistenceUnitProperties {

    public static final String AUTHORIZATION_SERVER_PERSISTENCE_UNIT_PROPERTIES_BEAN = "authorizationServerPersistenceUnitProperties";

    private boolean showSql;

    private boolean formatSql;

    private Integer batchSize;

    private final HikariConfig hikari = new HikariConfig();

    private final EntityManagerProps entityManager = new DefaultEntityManagerProperties();

    private final MigrationProperties migration = new DefaultMigrationProperties();

    private final VendorAdapterProps vendorAdapter = new DefaultVendorAdapterProperties();

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultMigrationProperties implements MigrationProperties {

        private boolean autoMigrationEnabled;

        private String changelog;

        private String contextName;

        private final Map<String, String> customVariables = new HashMap<>();
    }

    @Setter
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultVendorAdapterProperties implements VendorAdapterProps {

        private Boolean showSql;

        private Boolean prepareConnection;

        @Override
        public Optional<Boolean> isShowSql() {
            return Optional.ofNullable(showSql);
        }

        @Override
        public Optional<Boolean> isPrepareConnection() {
            return Optional.ofNullable(prepareConnection);
        }
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultEntityManagerProperties implements EntityManagerProps {

        private String persistenceUnitName;

        private String[] packagesToScan;

        private ValidationMode validationMode;

        private final JpaDialectConfig jpaDialect = new DefaultJpaDialectConfig();

        @Override
        public Optional<ValidationMode> getOptionalValidationMode() {
            return Optional.ofNullable(validationMode);
        }

        @Data
        @Validated
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DefaultJpaDialectConfig implements JpaDialectConfig {

            private JpaDialect instance;
        }
    }
}
