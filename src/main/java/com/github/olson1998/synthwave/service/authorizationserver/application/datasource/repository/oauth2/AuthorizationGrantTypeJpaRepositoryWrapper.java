package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthorizationGrantTypeJpaRepositoryWrapper implements AuthorizationGrantTypeDatasourceRepository {

    private final AuthorizationGrantTypeJpaRepository authorizationGrantTypeJpaRepository;

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypeSetByRegisteredClientId(Long registeredClientId) {
        return authorizationGrantTypeJpaRepository.selectAuthorizationGrantTypeByRegisteredClientId(registeredClientId);
    }
}
