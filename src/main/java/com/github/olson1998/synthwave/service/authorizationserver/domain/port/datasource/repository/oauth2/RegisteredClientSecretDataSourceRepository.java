package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientSecret;

public interface RegisteredClientSecretDataSourceRepository {

    boolean existsRegisteredClientId(Long registeredClientId);

    void save(RegisteredClientSecret registeredClientSecret);
}
