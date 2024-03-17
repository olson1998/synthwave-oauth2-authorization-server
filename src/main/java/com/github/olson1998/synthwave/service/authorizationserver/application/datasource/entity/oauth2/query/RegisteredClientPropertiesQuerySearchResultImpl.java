package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.query.RegisteredClientSearchQueryResult;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@Data
@AllArgsConstructor
public class RegisteredClientPropertiesQuerySearchResultImpl implements RegisteredClientSearchQueryResult {

    private final Long id;

    private final String clientId;

    private final MutableDateTime clientIdCreatedOn;

    private final String name;

    private final String clientSecret;

    private final MutableDateTime clientSecretExpireOn;

    private final Boolean clientSettingsRequireProofKey;

    private final Boolean clientSettingsRequireAuthorizationConsent;

    private final String clientSettingsJwkSetUrl;

    private final JwsAlgorithm clientSettingsJwsAlgorithm;

    private final SignatureAlgorithm tokenSettingsIdTokenSignatureAlgorithm;

    private final OAuth2TokenFormat tokenSettingsOAuth2TokenFormat;

    private final Duration tokenSettingsAccessTokenTimeToLive;

    private final Duration tokenSettingsRefreshTokenTimeToLive;

    private final Boolean tokenSettingsReuseRefreshTokens;

    private final Duration tokenSettingsDeviceCodeTimeToLive;

    private final Duration tokenSettingsAuthorizationCodeTimeToLive;

    @Override
    public RegisteredClient.Builder toBuilder() {
        var clientSettings = ClientSettings.builder()
                .requireProofKey(clientSettingsRequireProofKey)
                .jwkSetUrl(clientSettingsJwkSetUrl)
                .requireAuthorizationConsent(clientSettingsRequireAuthorizationConsent)
                .tokenEndpointAuthenticationSigningAlgorithm(clientSettingsJwsAlgorithm)
                .build();
        var tokenSettings = TokenSettings.builder()
                .idTokenSignatureAlgorithm(tokenSettingsIdTokenAlgorithm)
                .reuseRefreshTokens(tokenSettingsReuseRefreshTokens)
                .refreshTokenTimeToLive(tokenSettingsRefreshTokenTimeToLive)
                .accessTokenTimeToLive(tokenSettingsAccessTokenTimeToLive)
                .accessTokenFormat(tokenSettingsOAuth2TokenFormat)
                .deviceCodeTimeToLive(tokenSettingsDeviceCodeTimeToLive)
                .authorizationCodeTimeToLive(tokenSettingsAuthorizationCodeTimeToLive)
                .build();
        return RegisteredClient.withId(String.valueOf(id))
                .clientId(clientId)
                .clientName(name)
                .clientSecret(clientSecret)
                .clientIdIssuedAt(new MutableDateTimeConverter(clientIdCreatedOn).toJavaInstant())
                .clientSecretExpiresAt(new MutableDateTimeConverter(clientSecretExpireOn).toJavaInstant())
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings);
    }
}
