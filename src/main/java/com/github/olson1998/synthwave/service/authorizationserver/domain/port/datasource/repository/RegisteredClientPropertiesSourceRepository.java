package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import io.hypersistence.tsid.TSID;

import java.util.Optional;

public interface RegisteredClientPropertiesSourceRepository {

    Optional<RegisteredClientConfig> getRegisteredClientConfigByClientId(String username);

    Optional<RegisteredClientConfig> getRegisteredClientConfigByRegisteredClientId(TSID registeredClientId);

    void save(RegisteredClientEntity registeredClientEntity);
}
