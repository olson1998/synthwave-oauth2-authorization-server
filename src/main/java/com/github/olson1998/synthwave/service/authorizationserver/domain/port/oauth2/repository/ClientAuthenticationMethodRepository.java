package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ClientAuthenticationMethodRepository {

    Collection<? extends ClientAuthenticationMethodEntity> getAllMethods();

    Collection<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId);

    Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByExamples(Collection<? extends ClientAuthenticationMethodEntity> examples);

    @Transactional(rollbackFor = Exception.class)
    void saveBounds(Long registeredClientId, Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethods);

}
