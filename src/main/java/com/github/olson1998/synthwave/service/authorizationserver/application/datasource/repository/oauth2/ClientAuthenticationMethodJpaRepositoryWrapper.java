package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ClientAuthenticationMethodData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClientAuthenticationMethodJpaRepositoryWrapper implements ClientAuthenticationMethodDataSourceRepository {

    private final ClientAuthenticationMethodJpaRepository clientAuthenticationMethodJpaRepository;

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByMethod(Collection<ClientAuthenticationMethod> clientAuthenticationMethods) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodByMethods(clientAuthenticationMethods);
    }

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodsByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> saveAll(Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethodCollection) {
        var data = clientAuthenticationMethodCollection.stream()
                .map(ClientAuthenticationMethodData::new)
                .toList();
        return clientAuthenticationMethodJpaRepository.saveAll(data);
    }
}
