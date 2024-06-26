package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import com.github.olson1998.synthwave.support.jpa.spec.JpaSpecificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorizationGrantTypeJpaRepositoryWrapper implements AuthorizationGrantTypeDatasourceRepository {

    private final AuthorizationGrantTypeJpaRepository authorizationGrantTypeJpaRepository;

    private final AuthorizationGrantTypeBindingJpaRepository authorizationGrantTypeBindingJpaRepository;

    @Override
    public Collection<? extends AuthorizationGrantTypeEntity> getAuthorizationGrantTypeByExamples(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeCollection) {
        var examplesDataSpecChain = createAuthorizationGrantTypeDataExampleSpecChain(authorizationGrantTypeCollection);
        return authorizationGrantTypeJpaRepository.findAll(examplesDataSpecChain);
    }

    @Override
    public Collection<? extends AuthorizationGrantTypeEntity> getAllTypes() {
        return authorizationGrantTypeJpaRepository.findAll(Sort.by("id"));
    }

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypeSetByRegisteredClientId(Long registeredClientId) {
        return authorizationGrantTypeJpaRepository.selectAuthorizationGrantTypeByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends AuthorizationGrantTypeEntity> getAuthorizationGrantTypeByType(Collection<AuthorizationGrantType> authorizationGrantTypeCollection) {
        return authorizationGrantTypeJpaRepository.selectAuthorizationGrantTypeByType(authorizationGrantTypeCollection);
    }

    @Override
    public AuthorizationGrantTypeEntity save(AuthorizationGrantTypeEntity authorizationGrantType) {
        var data = new AuthorizationGrantTypeData(authorizationGrantType);
        return authorizationGrantTypeJpaRepository.save(data);
    }

    @Override
    public Collection<? extends AuthorizationGrantTypeEntity> saveAll(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeEntities) {
        var data = authorizationGrantTypeEntities.stream()
                .map(AuthorizationGrantTypeData::new)
                .toList();
        return authorizationGrantTypeJpaRepository.saveAll(data);
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

    private Specification<AuthorizationGrantTypeData> createAuthorizationGrantTypeDataExampleSpecChain(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeCollection) {
        return authorizationGrantTypeCollection.stream()
                .map(AuthorizationGrantTypeData::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), JpaSpecificationUtil::createDataExamplesSpecChain));
    }
}
