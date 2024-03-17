package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientPropertiesData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.AbstractRegisteredClientBuilderWrapper;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisteredClientPropertiesJpaRepositoryWrapper implements RegisteredClientDataSourceRepository {

    private final RegisteredClientPropertiesJpaRepository registeredClientPropertiesJpaRepository;

    @Override
    public Optional<AbstractRegisteredClientBuilderWrapper> findRegisteredClientByClientIdWithTimestamp(String clientId, MutableDateTime timestamp) {
        return registeredClientPropertiesJpaRepository.selectPropertiesByClientId(clientId, timestamp)
                .map(AbstractRegisteredClientBuilderWrapper.class::cast);
    }

    @Override
    public Optional<RegisteredClient.Builder> findRegisteredClientByIdWithTimestamp(Long id, MutableDateTime timestamp) {
        return registeredClientPropertiesJpaRepository.selectPropertiesByRegisteredClientId(id, timestamp)
                .map(RegisteredClient.Builder.class::cast);
    }

    @Override
    public RegisteredClientProperties saveRegisteredClientProperties(RegisteredClientProperties registeredClientProperties) {
        var data = new RegisteredClientPropertiesData(registeredClientProperties);
        return registeredClientPropertiesJpaRepository.save(data);
    }
}
