package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
public class StartupProvisioningProperties {

    private boolean enabled = false;

    private ProvisioningSource source = ProvisioningSource.FILE;

    private final RedirectProvisioningProperties redirectUriProvisioning = new RedirectProvisioningProperties();

    private final RegistrationClientProvisioningProperties registrationClientProvisioning = new RegistrationClientProvisioningProperties();

    public enum ProvisioningSource{
        FILE
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedirectProvisioningProperties{

        private final ProvisioningFileProperties file = new ProvisioningFileProperties();
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegistrationClientProvisioningProperties{

        private final ProvisioningFileProperties file = new ProvisioningFileProperties();
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvisioningFileProperties{

        private boolean thrownOnException = true;

        private String path;
    }
}
