package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.OAuth2TokenFormatJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.TokenSettingsEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.data.domain.Persistable;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "OAU2TSST")
public class TokenSettingsData implements Persistable<Long>, TokenSettingsEntity {

    @Id
    @Column(name = "RCID")
    private Long registeredClientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TSIDAG")
    private SignatureAlgorithm idTokenSignatureAlgorithm;

    @Column(name = "TSTFMT")
    @JavaType(OAuth2TokenFormatJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private OAuth2TokenFormat accessTokenFormat;

    @Column(name = "TSATTL")
    private Duration accessTokenTimeToLive;

    @Column(name = "TSRTTL")
    private Duration refreshTokenTimeToLive;

    @Column(name = "TSRTAR")
    private Boolean reuseRefreshTokens;

    @Column(name = "TSDCTL")
    private Duration deviceCodeTimeToLive;

    @Column(name = "TSACTL")
    private Duration authorizationCodeTimeToLive;

    public TokenSettingsData(TokenSettingsEntity tokenSettings) {
        this.registeredClientId = tokenSettings.getRegisteredClientId();
        this.idTokenSignatureAlgorithm = tokenSettings.getIdTokenSignatureAlgorithm();
        this.accessTokenFormat = tokenSettings.getAccessTokenFormat();
        this.accessTokenTimeToLive = tokenSettings.getAccessTokenTimeToLive();
        this.refreshTokenTimeToLive = tokenSettings.getRefreshTokenTimeToLive();
        this.reuseRefreshTokens = tokenSettings.getReuseRefreshTokens();
        this.deviceCodeTimeToLive = tokenSettings.getDeviceCodeTimeToLive();
        this.authorizationCodeTimeToLive = tokenSettings.getAuthorizationCodeTimeToLive();
    }

    @Override
    public TokenSettings toSettings() {
        return TokenSettings.builder()
                .idTokenSignatureAlgorithm(idTokenSignatureAlgorithm)
                .reuseRefreshTokens(reuseRefreshTokens)
                .refreshTokenTimeToLive(refreshTokenTimeToLive)
                .accessTokenTimeToLive(accessTokenTimeToLive)
                .accessTokenFormat(accessTokenFormat)
                .deviceCodeTimeToLive(deviceCodeTimeToLive)
                .authorizationCodeTimeToLive(authorizationCodeTimeToLive)
                .build();
    }

    @Override
    public Long getId() {
        return registeredClientId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
