package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationGrantTypeBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collection;

public interface AuthorizationGrantTypeBindDataSourceRepository {

    Collection<AuthorizationGrantType> getAuthorizationGrantTypesByRegisteredClientId(TSID registeredClientId);

    void saveAll(Collection<AuthorizationGrantTypeBinding> authorizationGrantTypeBindings);

}
