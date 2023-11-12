package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface RegistrationClientRepository {

    RegisteredClient getRegistrationClient(String clientId);
}
