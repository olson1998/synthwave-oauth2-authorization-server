package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.RegisteredClientSearchQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientPropertiesJpaRepositoryWrapper implements RegisteredClientDataSourceRepository {

    private final RegisteredClientPropertiesJpaRepository registeredClientPropertiesJpaRepository;

    @Override
    public Optional<RegisteredClientSearchQueryResult> findRegisteredClientByClientId(String clientId) {
        return registeredClientPropertiesJpaRepository.selectPropertiesByClientId(clientId)
                .map(RegisteredClientSearchQueryResult.class::cast);
    }

    @Override
    public Optional<RegisteredClientSearchQueryResult> findRegisteredClientById(Long id) {
        return registeredClientPropertiesJpaRepository.selectPropertiesByRegisteredClientId(id)
                .map(RegisteredClientSearchQueryResult.class::cast);
    }
}
