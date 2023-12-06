package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Client;
import io.hypersistence.tsid.TSID;

import java.util.Optional;

public interface RegisteredClientDataSourceRepository {

    boolean existsRegisteredClientForUsername(String username);

    Optional<RegisteredClientConfig> getRegisteredClientConfigByClientId(String username);

    Optional<RegisteredClientConfig> getRegisteredClientConfigByRegisteredClientId(TSID registeredClientId);

    RegisteredClientEntity save(OAuth2Client OAuth2Client);
}
