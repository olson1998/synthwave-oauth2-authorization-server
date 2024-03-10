package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.OAuth2TokenFormatJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.TokenSettings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Duration;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TSSDTA")
public class TokenSettingsData implements TokenSettings {

    @Id
    @Column(name = "RCID")
    private Long registeredClientId;

    @Column(name = "TSTF")
    @JavaType(OAuth2TokenFormatJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private OAuth2TokenFormat oAuth2TokenFormat;

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

}
