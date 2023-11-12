package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import io.hypersistence.tsid.TSID;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@ToString
public class RegisteredClientConfigImpl implements RegisteredClientConfig {

    private final TSID registeredClientId;

    private final String companyCode;

    private final String division;

    private final String clientId;

    private final String username;

    private final String passwordValue;

    private final Instant passwordExpireTime;

    private final TokenSettings tokenSettings;

    private final Set<String> redirectUris;

    private final Set<String> postLogoutRedirectUris;

    private final boolean requireProofKey;

    private final boolean requireAuthorizationConsent;

    private final Set<AuthorizationGrantType> authorizationGrantTypes;

    private final Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    public RegisteredClientConfigImpl(TSID registeredClientId,
                                      String companyCode,
                                      String division,
                                      String clientId,
                                      String username,
                                      TSID passwordId,
                                      String passwordValue,
                                      Period passwordExpirePeriod,
                                      Period authorizationCodeExpirePeriod,
                                      Period accessTokenExpirePeriod,
                                      OAuth2TokenFormat accessTokenFormat,
                                      Period deviceCodeExpirePeriod,
                                      boolean reuseRefreshToken,
                                      Period refreshTokenExpirePeriod,
                                      SignatureAlgorithm idTokenSignatureAlgorithm,
                                      boolean requireProofKey,
                                      boolean requireAuthorizationConsent) {
        this.registeredClientId = registeredClientId;
        this.companyCode = companyCode;
        this.division = division;
        this.clientId = clientId;
        this.username = username;
        this.passwordValue = passwordValue;
        this.requireProofKey = requireProofKey;
        this.requireAuthorizationConsent = requireAuthorizationConsent;
        this.passwordExpireTime = resolvePasswordExpireInstant(passwordId, passwordExpirePeriod);
        this.tokenSettings = resolveTokenSettings(
                authorizationCodeExpirePeriod,
                accessTokenExpirePeriod,
                accessTokenFormat,
                deviceCodeExpirePeriod,
                reuseRefreshToken,
                refreshTokenExpirePeriod,
                idTokenSignatureAlgorithm
        );
        this.redirectUris = new HashSet<>();
        this.postLogoutRedirectUris = new HashSet<>();
        this.authorizationGrantTypes = new HashSet<>();
        this.clientAuthenticationMethods = new HashSet<>();
    }

    @Override
    public RegisteredClientConfig withRedirectUris(Collection<RedirectURI> redirectUris) {
        appendUnresolvedUris(redirectUris);
        return this;
    }

    @Override
    public RegisteredClientConfig withAuthorizationGrantTypes(Collection<AuthorizationGrantType> authorizationGrantTypes) {
        this.authorizationGrantTypes.addAll(authorizationGrantTypes);
        return this;
    }

    @Override
    public RegisteredClientConfig withClientAuthenticationMethods(Collection<ClientAuthenticationMethod> clientAuthenticationMethods) {
        this.clientAuthenticationMethods.addAll(clientAuthenticationMethods);
        return this;
    }

    private void appendUnresolvedUris(@NonNull Collection<RedirectURI> redirectUrisCollection) {
        redirectUrisCollection.forEach(redirectUri -> {
            var uri = redirectUri.getRedirectUri();
            if(redirectUri.isPostLogin()){
                redirectUris.add(uri);
            } else if (redirectUri.isPostLogout()) {
                postLogoutRedirectUris.add(uri);
            }
        });
    }

    private TokenSettings resolveTokenSettings(Period authorizationCodeExpirePeriod,
                                               Period accessTokenExpirePeriod,
                                               OAuth2TokenFormat oAuth2TokenFormat,
                                               Period deviceCodeExpirePeriod,
                                               boolean reuseRefreshToken,
                                               Period refreshTokenExpirePeriod,
                                               SignatureAlgorithm idTokenSignatureAlgorithm){
        return TokenSettings.builder()
                .authorizationCodeTimeToLive(mapToDuration(authorizationCodeExpirePeriod))
                .accessTokenTimeToLive(mapToDuration(accessTokenExpirePeriod))
                .accessTokenFormat(oAuth2TokenFormat)
                .deviceCodeTimeToLive(mapToDuration(deviceCodeExpirePeriod))
                .reuseRefreshTokens(reuseRefreshToken)
                .refreshTokenTimeToLive(mapToDuration(refreshTokenExpirePeriod))
                .idTokenSignatureAlgorithm(idTokenSignatureAlgorithm)
                .build();
    }

    private Instant resolvePasswordExpireInstant(TSID passwordId, Period passwordExpirePeriod){
        return Optional.ofNullable(passwordExpirePeriod)
                .map(period -> countExpireInstant(passwordId, period))
                .orElse(null);
    }

    private Instant countExpireInstant(TSID tsid, Period expirePeriod){
        var creationInstant = tsid.getInstant();
        var creationDateTime = new DateTime(creationInstant.toEpochMilli());
        var expireDate = creationDateTime.plus(expirePeriod);
        return Instant.ofEpochMilli(expireDate.getMillis());
    }

    private Duration mapToDuration(Period period){
        return Optional.ofNullable(period)
                .map(p -> {
                    var expireMilis = p.getMillis();
                    return Duration.ofMillis(expireMilis);
                }).orElse(null);
    }

}
