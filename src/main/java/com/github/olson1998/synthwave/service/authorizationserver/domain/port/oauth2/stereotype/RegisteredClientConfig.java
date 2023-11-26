package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

public interface RegisteredClientConfig {

    String getCompanyCode();

    String getDivision();

    TSID getId();

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

    RegisteredClientConfig withRedirectEntities(Collection<RedirectEntity> redirectUris);

    RegisteredClientConfig withAuthorizationGrantTypes(Collection<AuthorizationGrantType> authorizationGrantTypes);

    RegisteredClientConfig withClientAuthenticationMethods(Collection<ClientAuthenticationMethod> clientAuthenticationMethods);

}
