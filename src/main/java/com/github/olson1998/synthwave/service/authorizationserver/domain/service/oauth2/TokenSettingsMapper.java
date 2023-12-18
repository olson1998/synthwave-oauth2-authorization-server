package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientProperties;
import com.github.olson1998.synthwave.support.joda.converter.PeriodConverter;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.Optional;

class TokenSettingsMapper {

    TokenSettings map(RegisteredClientProperties registeredClientProperties){
        var builder = TokenSettings.builder();
        var authorizationCodeTimeToLive = new PeriodConverter(registeredClientProperties.getAuthorizationCodeExpirePeriod())
                .toJavaDuration();
        var refreshTokenTimeToLive = new PeriodConverter(registeredClientProperties.getRefreshTokenExpirePeriod())
                .toJavaDuration();
        var accessTokenTimeToLive = new PeriodConverter(registeredClientProperties.getAccessTokenExpirePeriod())
                .toJavaDuration();
        builder.authorizationCodeTimeToLive(authorizationCodeTimeToLive);
        builder.idTokenSignatureAlgorithm(registeredClientProperties.getIdTokenSignatureAlgorithm());
        builder.refreshTokenTimeToLive(refreshTokenTimeToLive);
        builder.accessTokenTimeToLive(accessTokenTimeToLive);
        builder.accessTokenFormat(registeredClientProperties.getAccessTokenFormat());
        Optional.ofNullable(registeredClientProperties.getReuseRefreshToken()).ifPresent(builder::reuseRefreshTokens);
        return builder.build();
    }
}
