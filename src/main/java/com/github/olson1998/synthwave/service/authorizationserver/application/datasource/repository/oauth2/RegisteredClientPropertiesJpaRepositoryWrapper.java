package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientPropertiesJpaRepositoryWrapper implements RegisteredClientDataSourceRepository {

    private final RegisteredClientPropertiesJpaRepository registeredClientPropertiesJpaRepository;

    @Override
    public Optional<RegisteredClientProperties> findRegisteredClientByClientId(String clientId) {
        return Optional.empty();
    }

    @Override
    public Optional<RegisteredClientProperties> findRegisteredClientById(Long id) {
        return Optional.empty();
    }
}
