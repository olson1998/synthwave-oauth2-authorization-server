package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.OAuth2TokenFormatJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.TokenSettingsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenSettingsModel implements TokenSettingsEntity {

    @JsonProperty(value = "ID")
    private Long registeredClientId;

    @JsonProperty(value = "IDAG", required = true)
    private SignatureAlgorithm idTokenSignatureAlgorithm;

    @JsonProperty(value = "TFMT")
    private OAuth2TokenFormat oAuth2TokenFormat;

    @JsonProperty(value = "ATTL")
    private Duration accessTokenTimeToLive;

    @JsonProperty(value = "RTTL")
    private Duration refreshTokenTimeToLive;

    @JsonProperty(value = "RTAR")
    private Boolean reuseRefreshTokens;

    @JsonProperty(value = "DCTL")
    private Duration deviceCodeTimeToLive;

    @JsonProperty(value = "ACTL")
    private Duration authorizationCodeTimeToLive;

    @Override
    public TokenSettings toSettings() {
        return TokenSettings.builder()
                .idTokenSignatureAlgorithm(idTokenSignatureAlgorithm)
                .reuseRefreshTokens(reuseRefreshTokens)
                .refreshTokenTimeToLive(refreshTokenTimeToLive)
                .accessTokenTimeToLive(accessTokenTimeToLive)
                .accessTokenFormat(oAuth2TokenFormat)
                .deviceCodeTimeToLive(deviceCodeTimeToLive)
                .authorizationCodeTimeToLive(authorizationCodeTimeToLive)
                .build();
    }
}
