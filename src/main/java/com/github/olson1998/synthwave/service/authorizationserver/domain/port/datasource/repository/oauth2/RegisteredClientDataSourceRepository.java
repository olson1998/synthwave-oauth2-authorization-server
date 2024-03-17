package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.AbstractRegisteredClientBuilderWrapper;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

public interface RegisteredClientDataSourceRepository {

    Optional<? extends AbstractRegisteredClientBuilderWrapper> findRegisteredClientByClientIdWithTimestamp(String clientId, MutableDateTime timestamp);

    Optional<RegisteredClient.Builder> findRegisteredClientByIdWithTimestamp(Long id, MutableDateTime timestamp);

    RegisteredClientProperties save(RegisteredClientProperties registeredClientProperties);
}
