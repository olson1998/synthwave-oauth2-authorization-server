package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Client;
import io.hypersistence.tsid.TSID;

public interface RegisteredClientRepository {

    boolean isExistingRegisteredClientForUser(String username);

    TSID saveRegisteredClient(OAuth2Client OAuth2Client);

    void saveClientSecret(TSID registeredClientId, ClientSecret clientSecret);

    void saveClientSettings(RegisteredClientSettings registeredClientSettings);

    void saveTokenSettings(RegisteredClientTokenSettings registeredClientTokenSettings);
}
