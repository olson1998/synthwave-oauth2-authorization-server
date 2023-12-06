package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

public interface RegisteredClientRepository {

    Optional<RegisteredClient> findRegisteredClientById(TSID id);

    Optional<RegisteredClient> findRegisteredClientByClientId(String clientId);

    RegisteredClient saveRegisteredClient(RegisteredClient registeredClient);

    AbstractSynthWaveRegisteredClient saveSynthWaveRegisteredClient(AbstractSynthWaveRegisteredClient abstractSynthWaveRegisteredClient);

}
