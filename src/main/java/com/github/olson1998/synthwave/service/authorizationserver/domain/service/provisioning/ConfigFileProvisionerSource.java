package com.github.olson1998.synthwave.service.authorizationserver.domain.service.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.provisioning.repository.ProvisionerSource;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class ConfigFileProvisionerSource<T> implements ProvisionerSource<T> {

    private final ObjectMapper objectMapper;

    private final File configFile;

    private final TypeReference<T> typeReference;

    @Override
    public T getEntity() {
        try{
            return objectMapper.readValue(configFile, typeReference);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
