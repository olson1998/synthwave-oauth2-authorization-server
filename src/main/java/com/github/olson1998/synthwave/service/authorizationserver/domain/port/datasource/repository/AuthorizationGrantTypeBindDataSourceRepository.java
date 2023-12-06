package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationGrantTypeBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collection;
import java.util.Set;

public interface AuthorizationGrantTypeBindDataSourceRepository {

    Set<AuthorizationGrantType> getAuthorizationGrantTypesByRegisteredClientId(TSID registeredClientId);

    void save(AuthorizationGrantTypeBinding authorizationGrantTypeBinding);

    void saveAll(Collection<AuthorizationGrantTypeBinding> authorizationGrantTypeBindings);

}
