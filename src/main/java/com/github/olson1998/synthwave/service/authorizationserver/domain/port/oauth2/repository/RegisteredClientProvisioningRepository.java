package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface RegisteredClientProvisioningRepository {

    RegisteredClient provision(RegisteredClient registeredClient);

    void provisionSynthWaveClient(AbstractSynthWaveRegisteredClient synthWaveRegisteredClient);
}
