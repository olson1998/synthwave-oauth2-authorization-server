package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.SynthWaveRegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SynthWaveRegisteredClientDataSourceRepositoryProxy implements SynthWaveRegisteredClientDataSourceRepository {

    private final SynthWaveRegisteredClientJpaRepository synthWaveRegisteredClientJpaRepository;

    @Override
    public Optional<SynthWaveRegisteredClientProperties> getSynthWaveRegisteredClientPropsByClientId(String clientId) {
        return synthWaveRegisteredClientJpaRepository.selectSynthWaveRegisteredClientByClientId(clientId)
                .map(SynthWaveRegisteredClientProperties.class::cast);
    }

    @Override
    public Optional<SynthWaveRegisteredClientProperties> getSynthWaveRegisteredClientPropsByRegisteredClientId(TSID registeredClientId) {
        return synthWaveRegisteredClientJpaRepository.selectSynthWaveRegisteredClientByRegisteredClientId(registeredClientId)
                .map(SynthWaveRegisteredClientProperties.class::cast);
    }
}
