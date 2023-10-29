package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientProperites;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientDataSourceRepositoryProxy implements RegisteredClientPropertiesSourceRepository {

    private final RegisteredClientJpaRepository registeredClientDataJpaRepository;

    @Override
    public Optional<SynthWaveRegisteredClientProperties> getSynthWaveRegisteredClientPropsByClientId(String clientId) {
        return registeredClientDataJpaRepository.selectSynthWaveRegisteredClientByClientId(clientId)
                .map(SynthWaveRegisteredClientProperties.class::cast);
    }

    @Override
    public Optional<SynthWaveRegisteredClientProperties> getSynthWaveRegisteredClientPropsByRegisteredClientId(TSID registeredClientId) {
        return registeredClientDataJpaRepository.selectSynthWaveRegisteredClientByRegisteredClientId(registeredClientId)
                .map(SynthWaveRegisteredClientProperties.class::cast);
    }

    @Override
    public void save(RegisteredClientProperites registeredClientProperites) {
        var data = new RegisteredClientData(registeredClientProperites);
        registeredClientDataJpaRepository.save(data);
    }
}
