package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

public interface RegisteredClientProperties {

    String getCompanyCode();

    String getDivision();

    TSID getClientId();

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

    RegisteredClientProperties withRedirectUris(Collection<RedirectURI> redirectUris);

    RegisteredClientProperties withAuthorizationGrantTypes(Collection<AuthorizationGrantType> authorizationGrantTypes);

    RegisteredClientProperties withClientAuthenticationMethods(Collection<ClientAuthenticationMethod> clientAuthenticationMethods);

}
