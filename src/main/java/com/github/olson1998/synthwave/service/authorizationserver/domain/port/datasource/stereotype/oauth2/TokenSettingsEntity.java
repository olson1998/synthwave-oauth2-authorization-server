package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

public interface TokenSettingsEntity {

    Long getRegisteredClientId();

    SignatureAlgorithm getIdTokenSignatureAlgorithm();

    OAuth2TokenFormat getAccessTokenFormat();

    Duration getAccessTokenTimeToLive();

    Duration getRefreshTokenTimeToLive();

    Boolean getReuseRefreshTokens();

    Duration getDeviceCodeTimeToLive();

    Duration getAuthorizationCodeTimeToLive();

    TokenSettings toSettings();
}
