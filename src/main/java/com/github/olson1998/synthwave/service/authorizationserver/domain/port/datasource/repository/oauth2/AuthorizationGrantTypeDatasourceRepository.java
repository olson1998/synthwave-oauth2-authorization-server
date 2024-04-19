package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collection;
import java.util.Set;

public interface AuthorizationGrantTypeDatasourceRepository {

    Collection<Long> getAuthorizationGrantTypeIdByExamples(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeCollection);

    Collection<? extends AuthorizationGrantTypeEntity> getAllTypes();

    Set<AuthorizationGrantType> getAuthorizationGrantTypeSetByRegisteredClientId(Long registeredClientId);

    Collection<? extends AuthorizationGrantTypeEntity> getAuthorizationGrantTypeByType(Collection<AuthorizationGrantType> authorizationGrantTypeCollection);

    AuthorizationGrantTypeEntity save(AuthorizationGrantTypeEntity authorizationGrantType);

    Collection<? extends AuthorizationGrantTypeEntity> saveAll(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeEntities);

    void saveBounds(Collection<? extends AuthorizationGrantTypeBinding> authorizationGrantTypeBounds);

    int deleteBoundsByAuthorizationGrantTypeId(Long authorizationGrantTypeId);

    int deleteBoundsByAuthorizationGrantTypeIdAndRegisteredClientId(Long registeredClientId, Long authorizationGrantTypeId);

}
