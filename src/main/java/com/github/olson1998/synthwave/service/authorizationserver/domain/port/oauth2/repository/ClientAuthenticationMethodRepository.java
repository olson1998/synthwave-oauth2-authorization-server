package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

public interface ClientAuthenticationMethodRepository {

    Set<ClientAuthenticationMethod> getClientAuthenticationMethodByRegisteredClientId(TSID registeredClientId);

    void saveBindings(TSID registeredClientId, Set<ClientAuthenticationMethod> clientAuthenticationMethodSet);
}
