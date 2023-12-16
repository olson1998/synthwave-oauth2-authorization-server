package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.support.jackson.exception.IORuntimeException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
abstract class ProvisioningFileSupplier<T> {

    private final Logger log;

    private final String configFileLocation;

    private final ObjectMapper objectMapper;

    private final TypeReference<T> mappingTypeRef;

    Optional<T> supplyFromFile(){
        return findConfigFile()
                .map(this::readConfigFileJSON);
    }

    private Optional<File> findConfigFile(){
        return Optional.ofNullable(configFileLocation)
                .map(this::getProvisioningFile)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private Optional<File> getProvisioningFile(String filePath){
        try{
            return Optional.of(ResourceUtils.getFile(configFileLocation));
        }catch (IOException e){
            log.warn("Failed to read provisioning config file, reason:", e);
            return Optional.empty();
        }
    }

    private T readConfigFileJSON(File configFile) {
        try{
            return objectMapper.readValue(configFile, mappingTypeRef);
        }catch(IOException e){
            throw new IORuntimeException(e);
        }
    }
}
