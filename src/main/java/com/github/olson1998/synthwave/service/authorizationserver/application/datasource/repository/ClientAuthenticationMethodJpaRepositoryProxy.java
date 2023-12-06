package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.ClientAuthenticationMethodBoundData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.ClientAuthenticationMethodBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientAuthenticationMethodJpaRepositoryProxy implements ClientAuthenticationMethodBindDataSourceRepository {

    private final ClientAuthenticationMethodJpaRepository clientAuthenticationMethodJpaRepository;

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethodsByRegisteredClientId(@NonNull TSID registeredClientId) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodByRegisteredClientId(registeredClientId);
    }

    @Override
    public void save(ClientAuthenticationMethodBinding clientAuthenticationMethodBinding) {
        var data = new ClientAuthenticationMethodBoundData(clientAuthenticationMethodBinding);
        clientAuthenticationMethodJpaRepository.save(data);
    }

    @Override
    public void saveAll(@NonNull Collection<ClientAuthenticationMethodBinding> clientAuthenticationMethodBindings) {
        var data = clientAuthenticationMethodBindings.stream()
                .map(ClientAuthenticationMethodBoundData::new)
                .toList();
        clientAuthenticationMethodJpaRepository.saveAll(data);
    }
}
