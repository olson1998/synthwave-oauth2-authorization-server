package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public interface AuthorizationGrantTypeBinding {

    TSID getRegisteredClientId();

    AuthorizationGrantType getAuthorizationGrantType();
}
