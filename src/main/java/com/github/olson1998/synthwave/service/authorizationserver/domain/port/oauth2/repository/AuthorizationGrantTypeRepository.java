package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Set;

public interface AuthorizationGrantTypeRepository {

    Set<AuthorizationGrantType> getAuthorizationGrantTypesByRegisteredClientId(TSID registeredClientId);

    void saveBindings(TSID registeredClientId, Set<AuthorizationGrantType> authorizationGrantTypeSet);
}
