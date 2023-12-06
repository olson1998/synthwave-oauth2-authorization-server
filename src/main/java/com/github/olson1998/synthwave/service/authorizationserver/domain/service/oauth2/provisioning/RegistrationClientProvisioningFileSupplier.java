package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningSupplier;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.LinkedHashSet;

public class RegistrationClientProvisioningFileSupplier extends ProvisioningFileSupplier<LinkedHashSet<RegisteredClient>> implements RegistrationClientProvisioningSupplier {

    private static final TypeReference<LinkedHashSet<RegisteredClient>> MAPPING_TYPE_REF = new TypeReference<>() {
    };

    public RegistrationClientProvisioningFileSupplier(String configFileLocation, ObjectMapper objectMapper) {
        super(configFileLocation, objectMapper, MAPPING_TYPE_REF);
    }

    @Override
    public LinkedHashSet<RegisteredClient> get() {
        return supplyFromFile();
    }


}
