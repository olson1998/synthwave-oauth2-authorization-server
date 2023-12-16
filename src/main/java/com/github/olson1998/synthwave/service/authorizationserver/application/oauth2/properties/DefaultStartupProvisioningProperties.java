package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.properties;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.properties.ProvisioningProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.properties.StartupProvisioningProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.service.authorizationserver.oauth2.provisioning")
@ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.provisioning.enabled", havingValue = "true")
public class DefaultStartupProvisioningProperties implements StartupProvisioningProperties {

    private boolean enabled = false;

    private ProvisioningType type = ProvisioningType.SYNC;

    private ProvisioningSource source = ProvisioningSource.FILE;

    public enum ProvisioningSource{
        FILE
    }

    public enum ProvisioningType{
        SYNC,
        ASYNC
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedirectProvisioningProperties implements ProvisioningProperties{

        private boolean throwException = true;

        private final ProvisioningFileProperties file = new ProvisioningFileProperties();
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegistrationClientProvisioningProperties implements ProvisioningProperties{

        private boolean throwException = true;

        private final ProvisioningFileProperties file = new ProvisioningFileProperties();
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvisioningFileProperties{

        private String path;
    }
}
