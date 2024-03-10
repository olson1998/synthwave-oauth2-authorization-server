package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.OAuth2TokenFormatJavaType;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Duration;
import java.util.UUID;

public class TokenSettingsData {

    private Long registeredClientId;

    @JavaType(OAuth2TokenFormatJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private OAuth2TokenFormat oAuth2TokenFormat;

    private Duration accessTokenTimeToLive;

    private Duration refreshTokenTimeToLive;

    private Boolean reuseRefreshTokens;

    private Duration deviceCodeTimeToLive;

    private Duration authorizationCodeTimeToLive;

}
