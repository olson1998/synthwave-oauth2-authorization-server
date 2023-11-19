package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SynthWaveRegisteredClientRepository extends RegisteredClientRepository {

    @Override
    void save(RegisteredClient registeredClient);
}
