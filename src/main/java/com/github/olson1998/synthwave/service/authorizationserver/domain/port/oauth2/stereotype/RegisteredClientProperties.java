package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import io.hypersistence.tsid.TSID;
import org.joda.time.Period;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RegisteredClientProperties {

    TSID getId();

    String getCompanyCode();

    String getDivision();

    String getClientId();

    String getUsername();

    RegisteredClientSecret getRegisteredClientSecret();

    Period getAuthorizationCodeExpirePeriod();

    Period getAccessTokenExpirePeriod();

    Boolean getReuseRefreshToken();

    Period getRefreshTokenExpirePeriod();

    SignatureAlgorithm getIdTokenSignatureAlgorithm();

    OAuth2TokenFormat getAccessTokenFormat();

    Boolean getRequireProofKey();

    Boolean getRequireAuthorizationConsent();

    Set<String> getRedirectUris();

    Set<String> getPostLogoutRedirectUris();

    Optional<Boolean> findRequireProofKey();

    Optional<Boolean> findRequireAuthorizationConsent();

    Set<AuthorizationGrantType> getAuthorizationGrantTypes();

    Set<ClientAuthenticationMethod> getClientAuthenticationMethods();

    RegisteredClientProperties withRedirectEntities(Collection<RedirectEntity> redirectUris);

    RegisteredClientProperties withAuthorizationGrantTypes(Collection<AuthorizationGrantType> authorizationGrantTypes);

    RegisteredClientProperties withClientAuthenticationMethods(Collection<ClientAuthenticationMethod> clientAuthenticationMethods);

}
