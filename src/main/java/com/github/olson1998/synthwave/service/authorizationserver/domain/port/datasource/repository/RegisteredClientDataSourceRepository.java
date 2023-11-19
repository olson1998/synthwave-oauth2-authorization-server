package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientIdentifiers;
import io.hypersistence.tsid.TSID;

import java.util.Optional;

public interface RegisteredClientDataSourceRepository {

    Optional<String> getClientIdByUserId(TSID userId);

    Optional<RegisteredClientConfig> getRegisteredClientConfigByClientId(String username);

    Optional<RegisteredClientConfig> getRegisteredClientConfigByRegisteredClientId(TSID registeredClientId);

    RegisteredClientEntity save(RegisteredClientIdentifiers registeredClientIdentifiers);
}
