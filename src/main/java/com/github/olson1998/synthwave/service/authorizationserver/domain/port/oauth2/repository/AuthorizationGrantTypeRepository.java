package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorizationGrantTypeBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorizationGrantTypeResponse;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AuthorizationGrantTypeRepository {

    Collection<AuthorizationGrantType> getAuthorizationGrantTypesByRegisteredClientId(Long registeredClientId);

    Collection<? extends AuthorizationGrantTypeEntity> getAllTypes();

    Collection<? extends AuthorizationGrantTypeEntity> getAuthorizationGrantTypeByExamples(Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeEntityModels);

    @Transactional(rollbackFor = Exception.class)
    AuthorizationGrantTypeEntity save(AuthorizationGrantTypeEntity authorizationGrantType);

    @Transactional(rollbackFor = Exception.class)
    void saveBounds(Long registeredClientId, Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeCollection);

    @Transactional(rollbackFor = Exception.class)
    DeleteAuthorizationGrantTypeResponse delete(Long authorizationGrantTypeId);

    @Transactional(rollbackFor = Exception.class)
    DeleteAuthorizationGrantTypeBindingResponse deleteBinding(Long registeredClientId, Long authorizationGrantTypeId);

}
