package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RedirectProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Optional;

@Slf4j
public class RedirectProvisioningFileSupplier extends ProvisioningFileSupplier<LinkedHashSet<Redirect>> implements RedirectProvisioningSupplier {

    private static final TypeReference<LinkedHashSet<Redirect>> MAPPING_TYPE_REF = new TypeReference<>() {
    };

    public RedirectProvisioningFileSupplier(String configFileLocation, ObjectMapper objectMapper) {
        super(log, configFileLocation, objectMapper, MAPPING_TYPE_REF);
    }

    @Override
    public Optional<LinkedHashSet<Redirect>> get() {
        return supplyFromFile();
    }
}
