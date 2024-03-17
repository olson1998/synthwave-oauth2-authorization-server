package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.RegisteredClientSearchQueryResult;

import java.util.Optional;

public interface RegisteredClientDataSourceRepository {

    Optional<RegisteredClientSearchQueryResult> findRegisteredClientByClientId(String clientId);

    Optional<RegisteredClientSearchQueryResult> findRegisteredClientById(Long id);
}
