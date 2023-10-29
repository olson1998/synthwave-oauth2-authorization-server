package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.mapping.AuthorizationGrantTypeJavaType;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.OAuth2AuthorizationProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "OA2ADT")
public class OAuth2AuthorizationData implements OAuth2AuthorizationProperties {

    @Id
    @Column(name = "AUTHID",nullable = false)
    private String id;

    @Column(name = "PRNCPL",nullable = false)
    private String principal;

    @Column(name = "CID",nullable = false)
    private String registeredClientId;

    @Column(name = "AGRNTP",nullable = false)
    @JdbcType(VarcharJdbcType.class)
    @JavaType(AuthorizationGrantTypeJavaType.class)
    private AuthorizationGrantType authorizationGrantType;

    public OAuth2AuthorizationData(@NonNull OAuth2AuthorizationProperties synthWaveOAuth2Authorization) {
        this.id = synthWaveOAuth2Authorization.getId();
        this.principal = synthWaveOAuth2Authorization.getPrincipal();
        this.registeredClientId = synthWaveOAuth2Authorization.getRegisteredClientId();
        this.authorizationGrantType = synthWaveOAuth2Authorization.getAuthorizationGrantType();
    }
}