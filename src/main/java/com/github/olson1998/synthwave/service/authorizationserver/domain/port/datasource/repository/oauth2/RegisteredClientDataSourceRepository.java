package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.AbstractRegisteredClientBuilderWrapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.query.SearchRegisteredClient;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

public interface RegisteredClientDataSourceRepository {

    Page<? extends RegisteredClientProperties> searchRegisteredClient(SearchRegisteredClient searchRegisteredClient);

    Optional<? extends AbstractRegisteredClientBuilderWrapper> findRegisteredClientByClientIdWithTimestamp(String clientId, MutableDateTime timestamp);

    Optional<RegisteredClient.Builder> findRegisteredClientByIdWithTimestamp(Long id, MutableDateTime timestamp);

    RegisteredClientProperties save(RegisteredClientProperties registeredClientProperties);
}
