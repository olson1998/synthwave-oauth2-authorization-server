package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientRequestSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.RegistrationClientProvisioningService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning.RegistrationClientRequestFileSupplier;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProvisioningConfig {

    @Bean
    @ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.registration-client.provisioning.source", havingValue = "file", matchIfMissing = true)
    public RegistrationClientRequestSupplier registrationClientRequestSupplier(@Value("${synthwave.service.authorizationserver.oauth2.registration-client.provisioning.file:classpath:provisioning/_registration-client.json}")
                                                                               @NonNull String fileLocation,
                                                                               @NonNull ObjectMapper objectMapper){
        return new RegistrationClientRequestFileSupplier(fileLocation, objectMapper);
    }

    @Bean
    public RegistrationClientProvisioningRepository registrationClientProvisioningRepository(@NonNull RegistrationClientRequestSupplier registrationClientRequestSupplier,
                                                                                             @NonNull UserDetailsRepository userDetailsRepository,
                                                                                             @NonNull RegisteredClientProvisioningRepository registeredClientProvisioningRepository,
                                                                                             @NonNull RedirectRepository redirectRepository){
        return new RegistrationClientProvisioningService(
                registrationClientRequestSupplier,
                userDetailsRepository,
                registeredClientProvisioningRepository,
                redirectRepository
        );
    }

}
