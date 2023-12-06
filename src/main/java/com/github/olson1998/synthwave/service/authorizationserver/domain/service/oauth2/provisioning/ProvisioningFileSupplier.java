package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.support.jackson.exception.IORuntimeException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
abstract class ProvisioningFileSupplier<T> {

    private final String configFileLocation;

    private final ObjectMapper objectMapper;

    private final TypeReference<T> mappingTypeRef;

    T supplyFromFile(){
        var provisioningFile = getConfigFile();
        return readConfigFileJSON(provisioningFile);
    }

    private File getConfigFile(){
        try{
            return ResourceUtils.getFile(configFileLocation);
        }catch(IOException e){
            throw new IORuntimeException(e);
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
