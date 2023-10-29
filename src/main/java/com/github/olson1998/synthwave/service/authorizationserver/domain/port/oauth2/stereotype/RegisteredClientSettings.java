package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;

public interface RegisteredClientSettings {

    boolean isHuman();

    boolean isDevice();

    Set<ClientAuthenticationMethod> getClientAuthenticationMethods();

    Set<AuthorizationGrantType> getAuthorizationGrantTypes();

    ClientSettings.Builder fabricateClientSettingsBuilder();
}
