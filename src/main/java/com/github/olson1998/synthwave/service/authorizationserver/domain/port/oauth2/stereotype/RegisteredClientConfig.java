package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

public interface RegisteredClientConfig {

    TSID getRegisteredClientId();

    String getCompanyCode();

    String getDivision();

    String getClientId();

    String getUsername();

    String getPasswordValue();

    Instant getPasswordExpireTime();

    TokenSettings getTokenSettings();

    Set<String> getRedirectUris();

    Set<String> getPostLogoutRedirectUris();

    boolean isRequireProofKey();

    boolean isRequireAuthorizationConsent();

    Set<AuthorizationGrantType> getAuthorizationGrantTypes();

    Set<ClientAuthenticationMethod> getClientAuthenticationMethods();

    RegisteredClientConfig withRedirectUris(Collection<RedirectURI> redirectUris);

    RegisteredClientConfig withAuthorizationGrantTypes(Collection<AuthorizationGrantType> authorizationGrantTypes);

    RegisteredClientConfig withClientAuthenticationMethods(Collection<ClientAuthenticationMethod> clientAuthenticationMethods);

}
