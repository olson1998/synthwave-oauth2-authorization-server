package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OAuth2RegisteredClientRepository extends RegisteredClientRepository {

    @Override
    @Transactional
    void save(RegisteredClient registeredClient);
}
