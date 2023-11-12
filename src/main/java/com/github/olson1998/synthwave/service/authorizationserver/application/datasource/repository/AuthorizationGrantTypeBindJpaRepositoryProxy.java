package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthorizationGrantTypeBindDataSourceRepository;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthorizationGrantTypeBindJpaRepositoryProxy implements AuthorizationGrantTypeBindDataSourceRepository {

    private final AuthorizationGrantTypeBindJpaRepository authorizationGrantTypeBindJpaRepository;

    @Override
    public Collection<AuthorizationGrantType> getAuthorizationGrantTypesByRegisteredClientId(@NonNull TSID registeredClientId) {
        return authorizationGrantTypeBindJpaRepository.selectAuthorizationGrantTypeByRegisteredClientId(registeredClientId);
    }
}
