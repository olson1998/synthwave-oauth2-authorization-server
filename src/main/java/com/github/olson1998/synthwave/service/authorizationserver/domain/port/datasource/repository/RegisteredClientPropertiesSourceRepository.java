package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import io.hypersistence.tsid.TSID;

import java.util.Optional;

public interface RegisteredClientPropertiesSourceRepository {

    Optional<String> getClientIdByUserId(TSID userId);

    Optional<RegisteredClientProperties> getRegisteredClientConfigByClientId(String username);

    Optional<RegisteredClientProperties> getRegisteredClientConfigByRegisteredClientId(TSID registeredClientId);

    RegisteredClientEntity save(RegisteredClientEntity registeredClientEntity);
}
