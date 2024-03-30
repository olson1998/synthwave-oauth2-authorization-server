package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OAuth2RegisteredClientRepository extends RegisteredClientRepository {

    @Override
    @Transactional(rollbackFor = Exception.class)
    void save(RegisteredClient registeredClient);

    @Transactional(rollbackFor = Exception.class)
    void delete(Long registeredClientId);
}
