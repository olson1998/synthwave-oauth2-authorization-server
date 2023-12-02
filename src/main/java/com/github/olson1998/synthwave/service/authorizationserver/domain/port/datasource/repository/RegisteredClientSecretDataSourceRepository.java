package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;

public interface RegisteredClientSecretDataSourceRepository {

    void save(RegisteredClientSecret registeredClientSecret);
}
