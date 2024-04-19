package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.EntityProvider;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class FileEntityProvider<T> implements EntityProvider<T> {

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
