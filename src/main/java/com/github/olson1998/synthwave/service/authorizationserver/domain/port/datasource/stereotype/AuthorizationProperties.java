package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public interface AuthorizationProperties {

    String getId();

    String getRegisteredClientId();

    String getPrincipal();

    AuthorizationGrantType getAuthorizationGrantType();

    String getAttributesJSON();

}
