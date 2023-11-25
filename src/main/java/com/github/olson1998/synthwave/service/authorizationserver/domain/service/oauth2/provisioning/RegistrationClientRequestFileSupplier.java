package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientRequestSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.RegistrationClientProvisioningRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.LinkedHashSet;

@RequiredArgsConstructor
public class RegistrationClientRequestFileSupplier implements RegistrationClientRequestSupplier {

    private final String configFileLocation;

    private final ObjectMapper objectMapper;

    @Override
    public LinkedHashSet<RegistrationClientProvisioningRequest> get() {
        var configFile = getConfigFile();
        return mapToRequestsSet(configFile);
    }

    @SneakyThrows
    private File getConfigFile(){
        return ResourceUtils.getFile(configFileLocation);
    }

    @SneakyThrows
    private LinkedHashSet<RegistrationClientProvisioningRequest> mapToRequestsSet(File configFile){
        return objectMapper.readValue(configFile, new TypeReference<>() {
        });
    }
}
