package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Collection;
import java.util.Set;

public interface ClientAuthenticationMethodDataSourceRepository {

    Collection<? extends ClientAuthenticationMethodEntity> getAllAuthenticationMethods();

    Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByExamples(Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethodExamples);

    Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByMethod(Collection<ClientAuthenticationMethod> clientAuthenticationMethods);

    Set<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId);

    Collection<? extends ClientAuthenticationMethodEntity> saveAll(Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethodCollection);

    void saveAllBounds(Collection<? extends ClientAuthenticationMethodBinding> bounds);
}
