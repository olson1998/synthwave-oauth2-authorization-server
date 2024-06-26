package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.javatype.JwsAlgorithmJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientSettingsEntity;
import com.github.olson1998.synthwave.support.hibernate.javatype.MutableDateTimeJavaType;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOnEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.joda.time.MutableDateTime;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners({CreatedOnEntityListener.class})

@Entity
@Table(name = "OAU2CLST")
public class ClientSettingsData implements ClientSettingsEntity {

    @Id
    @Column(name = "RCID")
    private Long registeredClientId;

    @Column(name = "CSRPK")
    private Boolean requireProofKey;

    @Column(name = "CSRAC")
    private Boolean requireAuthorizationConsent;

    @Column(name = "CSJWSU")
    private String jwkSetUrl;

    @Column(name = "CSJWSA")
    @JavaType(JwsAlgorithmJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private JwsAlgorithm jwsAlgorithm;

    @Column(name = "CSCTMP")
    @JavaType(MutableDateTimeJavaType.class)
    @JdbcType(TimestampWithTimeZoneJdbcType.class)
    private MutableDateTime createdOn;

    public ClientSettingsData(ClientSettingsEntity clientSettings) {
        this.registeredClientId = clientSettings.getRegisteredClientId();
        this.requireProofKey = clientSettings.getRequireProofKey();
        this.requireAuthorizationConsent = clientSettings.getRequireAuthorizationConsent();
        this.jwkSetUrl = clientSettings.getJwkSetUrl();
        this.jwsAlgorithm = clientSettings.getJwsAlgorithm();
        this.createdOn = clientSettings.getCreatedOn();
    }

    @Override
    public ClientSettings toSettings() {
        return ClientSettings.builder()
                .requireProofKey(requireProofKey)
                .jwkSetUrl(jwkSetUrl)
                .requireAuthorizationConsent(requireAuthorizationConsent)
                .tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm)
                .build();
    }
}
