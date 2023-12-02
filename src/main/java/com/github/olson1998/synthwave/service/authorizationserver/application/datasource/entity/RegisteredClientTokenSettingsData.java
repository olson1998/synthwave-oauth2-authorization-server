package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AffiliationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.mapping.OAuth2TokenFormatJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;
import com.github.olson1998.synthwave.support.hibernate.javatype.PeriodJavaType;
import com.github.olson1998.synthwave.support.hibernate.javatype.TSIDJavaType;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.Period;
import org.springframework.data.domain.Persistable;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "O2ECTS")
public class RegisteredClientTokenSettingsData implements RegisteredClientTokenSettings, Persistable<TSID> {

    @Id
    @Column(name = "RCLID")
    @JdbcType(BigIntJdbcType.class)
    @JavaType(TSIDJavaType.class)
    private TSID registeredClientId;

    @Column(name = "EXPPAC", nullable = false)
    @JavaType(PeriodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Period authorizationCodeExpirePeriod;

    @Column(name = "EXPPAT", nullable = false)
    @JavaType(PeriodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Period accessTokenExpirePeriod;

    @Column(name = "ACCTKF", nullable = false)
    @JavaType(OAuth2TokenFormatJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private OAuth2TokenFormat accessTokenFormat;

    @Column(name = "EXPPDC", nullable = false)
    @JavaType(PeriodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Period deviceCodeExpirePeriod;

    @Column(name = "RRFTKN", nullable = false)
    private Boolean reuseRefreshToken;

    @Column(name = "EXPPRT", nullable = false)
    @JavaType(PeriodJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Period refreshTokenExpirePeriod;

    @Column(name = "ITKALG", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SignatureAlgorithm idTokenSignatureAlgorithm;

    public RegisteredClientTokenSettingsData(@NonNull RegisteredClientTokenSettings registeredClientTokenSettings) {
        this.registeredClientId = registeredClientTokenSettings.getRegisteredClientId();
        this.authorizationCodeExpirePeriod = registeredClientTokenSettings.getAuthorizationCodeExpirePeriod();
        this.accessTokenExpirePeriod = registeredClientTokenSettings.getAccessTokenExpirePeriod();
        this.accessTokenFormat = registeredClientTokenSettings.getAccessTokenFormat();
        this.deviceCodeExpirePeriod = registeredClientTokenSettings.getDeviceCodeExpirePeriod();
        this.reuseRefreshToken = registeredClientTokenSettings.getReuseRefreshToken();
        this.refreshTokenExpirePeriod = registeredClientTokenSettings.getRefreshTokenExpirePeriod();
        this.idTokenSignatureAlgorithm = registeredClientTokenSettings.getIdTokenSignatureAlgorithm();
    }

    @Override
    public TSID getId() {
        return registeredClientId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
