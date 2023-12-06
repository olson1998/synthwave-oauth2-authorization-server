package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RedirectProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;

import java.util.LinkedHashSet;

public class RedirectProvisioningFileSupplier extends ProvisioningFileSupplier<LinkedHashSet<Redirect>> implements RedirectProvisioningSupplier {

    private static final TypeReference<LinkedHashSet<Redirect>> MAPPING_TYPE_REF = new TypeReference<>() {
    };

    public RedirectProvisioningFileSupplier(String configFileLocation, ObjectMapper objectMapper) {
        super(configFileLocation, objectMapper, MAPPING_TYPE_REF);
    }

    @Override
    public LinkedHashSet<Redirect> get() {
        return supplyFromFile();
    }
}
