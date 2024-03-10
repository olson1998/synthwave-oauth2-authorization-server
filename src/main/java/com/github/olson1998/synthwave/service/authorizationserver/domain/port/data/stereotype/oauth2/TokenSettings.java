package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2;

import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Duration;

public interface TokenSettings {

    Long getRegisteredClientId();

    OAuth2TokenFormat getOAuth2TokenFormat();

    Duration getAccessTokenTimeToLive();

    Duration getRefreshTokenTimeToLive();

    Boolean getReuseRefreshTokens();

    Duration getDeviceCodeTimeToLive();

    Duration getAuthorizationCodeTimeToLive();
}
