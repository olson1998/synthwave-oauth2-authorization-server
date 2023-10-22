package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectUrisDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.SynthWaveRegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@RequiredArgsConstructor
public class SynthWaveRegisteredClientService implements SynthWaveRegisteredClientRepository {

    private final RedirectUrisDataSourceRepository redirectUrisDataSourceRepository;

    private final SynthWaveRegisteredClientDataSourceRepository synthWaveRegisteredClientDataSourceRepository;

    private final SynthWaveRegisteredClientPropertiesMapper synthWaveRegisteredClientPropertiesMapper =
            new SynthWaveRegisteredClientPropertiesMapper();

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        var longId = Long.getLong(id);
        var tsid = TSID.from(longId);
        return synthWaveRegisteredClientDataSourceRepository.getSynthWaveRegisteredClientPropsByRegisteredClientId(tsid)
                .map(this::mapRedirectUris)
                .map(synthWaveRegisteredClientPropertiesMapper::map)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return synthWaveRegisteredClientDataSourceRepository.getSynthWaveRegisteredClientPropsByClientId(clientId)
                .map(this::mapRedirectUris)
                .map(synthWaveRegisteredClientPropertiesMapper::map)
                .orElse(null);
    }

    private SynthWaveRegisteredClientProperties mapRedirectUris(SynthWaveRegisteredClientProperties synthWaveRegisteredClientProperties){
        var redirectUris = redirectUrisDataSourceRepository.getAllRedirectUris();
        synthWaveRegisteredClientProperties.appendUnresolvedUris(redirectUris);
        return synthWaveRegisteredClientProperties;
    }

}
