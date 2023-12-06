package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Slf4j
@RequiredArgsConstructor
public class SynthWaveRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        registeredClientRepository.saveRegisteredClient(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        log.debug("Searching client: '%s'".formatted(id));
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return registeredClientRepository.findRegisteredClientById(tsid)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        log.debug("Searching client: '%s'".formatted(clientId));
        return registeredClientRepository.findRegisteredClientByClientId(clientId)
                .orElse(null);
    }

}
