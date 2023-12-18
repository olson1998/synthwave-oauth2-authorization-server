package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Client;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientJpaRepositoryProxy implements RegisteredClientDataSourceRepository {

    private final RegisteredClientJpaRepository registeredClientDataJpaRepository;

    @Override
    public boolean existsRegisteredClientForUsername(@NonNull String username) {
        return registeredClientDataJpaRepository.selectCaseWhenCountRegisteredClientWithUsernameIsGreaterThanZero(username);
    }

    @Override
    public Optional<RegisteredClientProperties> getRegisteredClientConfigByClientId(@NonNull String clientId) {
        return registeredClientDataJpaRepository.selectRegisteredClientConfigByClientId(clientId)
                .map(RegisteredClientProperties.class::cast);
    }

    @Override
    public Optional<RegisteredClientProperties> getRegisteredClientConfigByRegisteredClientId(@NonNull TSID registeredClientId) {
        return registeredClientDataJpaRepository.selectSynthWaveRegisteredClientByRegisteredClientId(registeredClientId)
                .map(RegisteredClientProperties.class::cast);
    }

    @Override
    public RegisteredClientEntity save(@NonNull OAuth2Client OAuth2Client) {
        var data = new RegisteredClientData(OAuth2Client);
        return registeredClientDataJpaRepository.save(data);
    }

}
