package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

public interface ClientAuthenticationMethodDataSourceRepository {

    Set<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId);
}
