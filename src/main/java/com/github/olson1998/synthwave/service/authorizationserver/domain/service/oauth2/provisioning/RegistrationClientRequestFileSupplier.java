package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientRequestSupplier;
import com.github.olson1998.synthwave.support.jackson.exception.IORuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

@RequiredArgsConstructor
public class RegistrationClientRequestFileSupplier implements RegistrationClientRequestSupplier {

    private final String configFileLocation;

    private final ObjectMapper objectMapper;

    @Override
    public LinkedHashSet<RegisteredClient> get() {
        var configFile = getConfigFile();
        return readConfigFileJSON(configFile);
    }

    private File getConfigFile(){
        try{
            return ResourceUtils.getFile(configFileLocation);
        }catch(IOException e){
            throw new IORuntimeException(e);
        }
    }

    private LinkedHashSet<RegisteredClient> readConfigFileJSON(File configFile) {
        try{
            return objectMapper.readValue(configFile, new TypeReference<>() {
            });
        }catch(IOException e){
            throw new IORuntimeException(e);
        }
    }
}
