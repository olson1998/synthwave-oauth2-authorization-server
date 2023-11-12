package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.ClientAuthenticationMethodBindDataSourceRepository;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ClientAuthenticationMethodJpaRepositoryProxy implements ClientAuthenticationMethodBindDataSourceRepository {

    private final ClientAuthenticationMethodJpaRepository clientAuthenticationMethodJpaRepository;

    @Override
    public Collection<ClientAuthenticationMethod> getClientAuthenticationMethodsByRegisteredClientId(@NonNull TSID registeredClientId) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodByRegisteredClientId(registeredClientId);
    }
}
