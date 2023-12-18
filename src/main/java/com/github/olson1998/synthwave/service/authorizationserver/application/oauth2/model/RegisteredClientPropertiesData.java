package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import io.hypersistence.tsid.TSID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.Period;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class RegisteredClientPropertiesData implements RegisteredClientProperties {

    private final TSID id;

    private final String companyCode;

    private final String division;

    private final String clientId;

    private final String username;

    private final RegisteredClientSecret registeredClientSecret;

    private final Period authorizationCodeExpirePeriod;

    private final Period accessTokenExpirePeriod;

    private final OAuth2TokenFormat accessTokenFormat;

    private final Period deviceCodeExpirePeriod;

    private final Boolean reuseRefreshToken;

    private final Period refreshTokenExpirePeriod;

    private final SignatureAlgorithm idTokenSignatureAlgorithm;

    private final Boolean requireProofKey;

    private final Boolean requireAuthorizationConsent;

    private final Set<String> redirectUris= new HashSet<>();

    private final Set<String> postLogoutRedirectUris= new HashSet<>();

    private final Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();

    private final Set<ClientAuthenticationMethod> clientAuthenticationMethods = new HashSet<>();

    @Override
    public Optional<Boolean> findRequireProofKey() {
        return Optional.ofNullable(requireProofKey);
    }

    @Override
    public Optional<Boolean> findRequireAuthorizationConsent() {
        return Optional.ofNullable(requireAuthorizationConsent);
    }

    @Override
    public RegisteredClientProperties withRedirectEntities(Collection<RedirectEntity> redirectUris) {
        appendUnresolvedUris(redirectUris);
        return this;
    }

    @Override
    public RegisteredClientProperties withAuthorizationGrantTypes(Collection<AuthorizationGrantType> authorizationGrantTypes) {
        this.authorizationGrantTypes.addAll(authorizationGrantTypes);
        return this;
    }

    @Override
    public RegisteredClientProperties withClientAuthenticationMethods(Collection<ClientAuthenticationMethod> clientAuthenticationMethods) {
        this.clientAuthenticationMethods.addAll(clientAuthenticationMethods);
        return this;
    }

    private void appendUnresolvedUris(@NonNull Collection<RedirectEntity> redirectUrisCollection) {
        redirectUrisCollection.forEach(redirectUri -> {
            var uri = redirectUri.getUri();
            if(redirectUri.isPostLogin()){
                redirectUris.add(uri);
            } else if (redirectUri.isPostLogout()) {
                postLogoutRedirectUris.add(uri);
            }
        });
    }
}
