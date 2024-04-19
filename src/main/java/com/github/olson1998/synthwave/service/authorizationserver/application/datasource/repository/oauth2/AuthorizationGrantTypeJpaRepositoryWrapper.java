package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthorizationGrantTypeJpaRepositoryWrapper implements AuthorizationGrantTypeDatasourceRepository {

    private final AuthorizationGrantTypeJpaRepository authorizationGrantTypeJpaRepository;

    private final AuthorizationGrantTypeBindingJpaRepository authorizationGrantTypeBindingJpaRepository;

    @Override
    public Collection<Long> getAuthorizationGrantTypeIdByExamples(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeCollection) {
        var examples = authorizationGrantTypeCollection.stream()
                .map(AuthorizationGrantTypeData::new)
                .map(authorizationGrantTypeData -> Example.of(authorizationGrantTypeData, ExampleMatcher.matching().withIgnoreNullValues()))
                .toList();
        return authorizationGrantTypeJpaRepository.selectAuthorizationGrantTypeIdByExamples(examples);
    }

    @Override
    public Collection<? extends AuthorizationGrantTypeEntity> getAllTypes() {
        return authorizationGrantTypeJpaRepository.findAll();
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypeSetByRegisteredClientId(Long registeredClientId) {
        return authorizationGrantTypeJpaRepository.selectAuthorizationGrantTypeByRegisteredClientId(registeredClientId);
    }

    @Override
    public AuthorizationGrantTypeEntity save(AuthorizationGrantTypeEntity authorizationGrantType) {
        var data = new AuthorizationGrantTypeData(authorizationGrantType);
        return authorizationGrantTypeJpaRepository.save(data);
    }

    @Override
    public void saveBounds(Collection<? extends AuthorizationGrantTypeBinding> authorizationGrantTypeBounds) {
        var data = authorizationGrantTypeBounds.stream()
                .map(AuthorizationGrantTypeBindingData::new)
                .toList();
        authorizationGrantTypeBindingJpaRepository.saveAll(data);
    }

    @Override
    public int deleteBoundsByAuthorizationGrantTypeId(Long authorizationGrantTypeId) {
        return authorizationGrantTypeBindingJpaRepository.deleteAuthorizationGrantTypeBindingByAuthorizationGrantTypeId(authorizationGrantTypeId);
    }

    @Override
    public int deleteBoundsByAuthorizationGrantTypeIdAndRegisteredClientId(Long registeredClientId, Long authorizationGrantTypeId) {
        return authorizationGrantTypeBindingJpaRepository.deleteAuthorizationGrantTypeBindingByAuthorizationGrantTypeIdAndRegisteredClientId(
                registeredClientId,
                authorizationGrantTypeId
        );
    }
}
