package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientJpaRepositoryProxy implements RegisteredClientPropertiesSourceRepository {

    private final RegisteredClientJpaRepository registeredClientDataJpaRepository;

    @Override
    public Optional<String> getClientIdByUserId(TSID userId) {
        return registeredClientDataJpaRepository.selectClientIdByUserId(userId);
    }

    @Override
    public Optional<RegisteredClientProperties> getRegisteredClientConfigByClientId(String clientId) {
        return registeredClientDataJpaRepository.selectRegisteredClientConfigByClientId(clientId)
                .map(RegisteredClientProperties.class::cast);
    }

    @Override
    public Optional<RegisteredClientProperties> getRegisteredClientConfigByRegisteredClientId(TSID registeredClientId) {
        return registeredClientDataJpaRepository.selectSynthWaveRegisteredClientByRegisteredClientId(registeredClientId)
                .map(RegisteredClientProperties.class::cast);
    }

    @Override
    public RegisteredClientEntity save(RegisteredClientEntity registeredClientEntity) {
        var data = new RegisteredClientData(registeredClientEntity);
        return registeredClientDataJpaRepository.save(data);
    }
}
