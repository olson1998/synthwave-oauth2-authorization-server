package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.support.joda.converter.PeriodConverter;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.Optional;

class TokenSettingsMapper {

    TokenSettings map(RegisteredClientConfig registeredClientConfig){
        var builder = TokenSettings.builder();
        var authorizationCodeTimeToLive = new PeriodConverter(registeredClientConfig.getAuthorizationCodeExpirePeriod())
                .toJavaDuration();
        var refreshTokenTimeToLive = new PeriodConverter(registeredClientConfig.getRefreshTokenExpirePeriod())
                .toJavaDuration();
        var accessTokenTimeToLive = new PeriodConverter(registeredClientConfig.getAccessTokenExpirePeriod())
                .toJavaDuration();
        builder.authorizationCodeTimeToLive(authorizationCodeTimeToLive);
        builder.idTokenSignatureAlgorithm(registeredClientConfig.getIdTokenSignatureAlgorithm());
        builder.refreshTokenTimeToLive(refreshTokenTimeToLive);
        builder.accessTokenTimeToLive(accessTokenTimeToLive);
        builder.accessTokenFormat(registeredClientConfig.getAccessTokenFormat());
        Optional.ofNullable(registeredClientConfig.getReuseRefreshToken()).ifPresent(builder::reuseRefreshTokens);
        return builder.build();
    }
}
