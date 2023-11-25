package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.AuthorizationGrantTypeBoundModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationGrantTypeBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collection;

class AuthorizationGrantTypesBoundMapper {

    Collection<AuthorizationGrantTypeBinding> mapToAuthorizationGrantTypeBindings(TSID registeredClientId, Collection<AuthorizationGrantType> authorizationGrantTypes){
        return authorizationGrantTypes.stream()
                .map(authorizationGrantType -> new AuthorizationGrantTypeBoundModel(registeredClientId, authorizationGrantType))
                .map(AuthorizationGrantTypeBinding.class::cast)
                .toList();
    }
}
