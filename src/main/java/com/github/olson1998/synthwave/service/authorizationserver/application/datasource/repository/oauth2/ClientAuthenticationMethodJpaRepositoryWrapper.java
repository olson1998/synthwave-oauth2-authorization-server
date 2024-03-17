package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClientAuthenticationMethodJpaRepositoryWrapper implements ClientAuthenticationMethodDataSourceRepository {

    private final ClientAuthenticationMethodJpaRepository clientAuthenticationMethodJpaRepository;

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodsByRegisteredClientId(registeredClientId);
    }
}
