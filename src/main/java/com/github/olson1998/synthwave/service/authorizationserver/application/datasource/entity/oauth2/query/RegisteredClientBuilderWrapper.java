package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.AbstractRegisteredClientBuilderWrapper;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegisteredClientBuilderWrapper extends AbstractRegisteredClientBuilderWrapper {

    @Getter
    private final Long id;

    public RegisteredClientBuilderWrapper(Long id,
                                          String clientId,
                                          MutableDateTime clientIdCreatedOn,
                                          String name,
                                          String clientSecret,
                                          MutableDateTime clientSecretExpireOn,
                                          Boolean clientSettingsRequireProofKey,
                                          Boolean clientSettingsRequireAuthorizationConsent,
                                          String clientSettingsJwkSetUrl,
                                          JwsAlgorithm clientSettingsJwsAlgorithm,
                                          SignatureAlgorithm tokenSettingsIdTokenSignatureAlgorithm,
                                          OAuth2TokenFormat tokenSettingsOAuth2TokenFormat,
                                          Duration tokenSettingsAccessTokenTimeToLive,
                                          Duration tokenSettingsRefreshTokenTimeToLive,
                                          Boolean tokenSettingsReuseRefreshTokens,
                                          Duration tokenSettingsDeviceCodeTimeToLive,
                                          Duration tokenSettingsAuthorizationCodeTimeToLive) {
        super(String.valueOf(id));
        this.id = id;
        this.clientId(clientId);
        this.clientIdIssuedAt(new MutableDateTimeConverter(clientIdCreatedOn).toJavaInstant());
        this.clientName(name);
        this.clientSecret(clientSecret);
        this.clientSecretExpiresAt(new MutableDateTimeConverter(clientSecretExpireOn).toJavaInstant());
        var clientSettings = ClientSettings.builder()
                .requireProofKey(clientSettingsRequireProofKey)
                .jwkSetUrl(clientSettingsJwkSetUrl)
                .requireAuthorizationConsent(clientSettingsRequireAuthorizationConsent)
                .tokenEndpointAuthenticationSigningAlgorithm(clientSettingsJwsAlgorithm)
                .build();
        var tokenSettings = TokenSettings.builder()
                .idTokenSignatureAlgorithm(tokenSettingsIdTokenSignatureAlgorithm)
                .reuseRefreshTokens(tokenSettingsReuseRefreshTokens)
                .refreshTokenTimeToLive(tokenSettingsRefreshTokenTimeToLive)
                .accessTokenTimeToLive(tokenSettingsAccessTokenTimeToLive)
                .accessTokenFormat(tokenSettingsOAuth2TokenFormat)
                .deviceCodeTimeToLive(tokenSettingsDeviceCodeTimeToLive)
                .authorizationCodeTimeToLive(tokenSettingsAuthorizationCodeTimeToLive)
                .build();
        this.clientSettings(clientSettings);
        this.tokenSettings(tokenSettings);
    }

}
