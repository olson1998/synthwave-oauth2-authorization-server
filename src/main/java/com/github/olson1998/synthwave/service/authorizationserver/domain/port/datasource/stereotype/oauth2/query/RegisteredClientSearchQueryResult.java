package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface RegisteredClientSearchQueryResult {

    Long getId();

    RegisteredClient.Builder toBuilder();

}
