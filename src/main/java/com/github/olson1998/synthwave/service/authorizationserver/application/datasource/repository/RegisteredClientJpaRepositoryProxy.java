package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientIdentifiers;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientJpaRepositoryProxy implements RegisteredClientSourceRepository {

    private final RegisteredClientJpaRepository registeredClientDataJpaRepository;

    @Override
    public Optional<String> getClientIdByUserId(TSID userId) {
        return registeredClientDataJpaRepository.selectClientIdByUserId(userId);
    }

    @Override
    public Optional<RegisteredClientConfig> getRegisteredClientConfigByClientId(String clientId) {
        return registeredClientDataJpaRepository.selectRegisteredClientConfigByClientId(clientId)
                .map(RegisteredClientConfig.class::cast);
    }

    @Override
    public Optional<RegisteredClientConfig> getRegisteredClientConfigByRegisteredClientId(TSID registeredClientId) {
        return registeredClientDataJpaRepository.selectSynthWaveRegisteredClientByRegisteredClientId(registeredClientId)
                .map(RegisteredClientConfig.class::cast);
    }

    @Override
    public RegisteredClientEntity save(@NonNull RegisteredClientIdentifiers registeredClientIdentifiers) {
        var data = new RegisteredClientData(registeredClientIdentifiers);
        return registeredClientDataJpaRepository.save(data);
    }

}
