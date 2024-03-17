package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;

import java.util.Optional;

public interface RegisteredClientDataSourceRepository {

    Optional<RegisteredClientProperties> findRegisteredClientByClientId(String clientId);

    Optional<RegisteredClientProperties> findRegisteredClientById(Long id);
}
