package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientProperites;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientJpaRepositoryProxy implements RegisteredClientPropertiesSourceRepository {

    private final RegisteredClientJpaRepository registeredClientDataJpaRepository;

    @Override
    public Optional<RegisteredClientConfig> getRegisteredClientConfigByClientId(String clientId) {
        return registeredClientDataJpaRepository.selectRegisteredClientConfigByClientId(clientId)
                .map(RegisteredClientConfig.class::cast);
    }

    @Override
    public Optional<RegisteredClientConfig> getSynthWaveRegisteredClientPropsByRegisteredClientId(TSID registeredClientId) {
        return registeredClientDataJpaRepository.selectSynthWaveRegisteredClientByRegisteredClientId(registeredClientId)
                .map(RegisteredClientConfig.class::cast);
    }

    @Override
    public void save(RegisteredClientProperites registeredClientProperites) {
        var data = new RegisteredClientData(registeredClientProperites);
        registeredClientDataJpaRepository.save(data);
    }
}
