package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.StartupProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.StartupProvisioningService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.RegistrationClientProvisioningFileSupplier;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProvisioningConfig {

    @Bean
    @ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.registration-client.provisioning.source", havingValue = "file", matchIfMissing = true)
    public RegistrationClientProvisioningSupplier registrationClientRequestSupplier(@Value("${synthwave.service.authorizationserver.oauth2.registration-client.provisioning.file:classpath:provisioning/_registration-client.json}")
                                                                               @NonNull String fileLocation,
                                                                                    @NonNull ObjectMapper objectMapper){
        return new RegistrationClientProvisioningFileSupplier(fileLocation, objectMapper);
    }

    @Bean
    public StartupProvisioningRepository registrationClientProvisioningRepository(@NonNull RegistrationClientProvisioningSupplier registrationClientProvisioningSupplier,
                                                                                  @NonNull RegisteredClientProvisioningRepository registeredClientProvisioningRepository){
        return new StartupProvisioningService(
                registrationClientProvisioningSupplier,
                registeredClientProvisioningRepository
        );
    }

}
