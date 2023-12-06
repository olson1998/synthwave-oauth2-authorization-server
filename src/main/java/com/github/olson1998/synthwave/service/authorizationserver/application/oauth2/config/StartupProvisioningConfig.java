package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RedirectProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.StartupProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.RedirectProvisioningFileSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.StartupProvisioningService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.RegistrationClientProvisioningFileSupplier;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupProvisioningConfig {

    @Bean
    @ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.provisioning.source", havingValue = "file", matchIfMissing = true)
    public RegistrationClientProvisioningSupplier registrationClientRequestSupplier(@Value("${synthwave.service.authorizationserver.oauth2.provisioning.registration-client.file:classpath:provisioning/_registration-client.json}")
                                                                                    @NonNull String fileLocation,
                                                                                    @NonNull ObjectMapper objectMapper){
        return new RegistrationClientProvisioningFileSupplier(fileLocation, objectMapper);
    }

    @Bean
    @ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.provisioning.source", havingValue = "file", matchIfMissing = true)
    public RedirectProvisioningSupplier redirectProvisioningSupplier(@Value("${synthwave.service.authorizationserver.oauth2.provisioning.redirect.file:classpath:provisioning/_redirect.json}")
                                                                     @NonNull String fileLocation,
                                                                     @NonNull ObjectMapper objectMapper){
        return new RedirectProvisioningFileSupplier(fileLocation, objectMapper);
    }

    @Bean
    public StartupProvisioningRepository startupProvisioningRepository(@NonNull RedirectProvisioningSupplier redirectProvisioningSupplier,
                                                                       @NonNull RegistrationClientProvisioningSupplier registrationClientProvisioningSupplier,
                                                                       @NonNull RedirectRepository redirectRepository,
                                                                       @NonNull RegisteredClientRepository registeredClientRepository){
        return new StartupProvisioningService(
                redirectProvisioningSupplier,
                registrationClientProvisioningSupplier,
                redirectRepository,
                registeredClientRepository
        );
    }

}
