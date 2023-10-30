package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.java.ClassJavaType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class OAuth2TokenDesc implements Serializable {

    private static final long serialVersionUID = 1567017322902337844L;
    @Column(name = "AUTHID", nullable = false)
    private String authorizationId;

    @Column(name = "JVCLASS", nullable = false)
    @JavaType(ClassJavaType.class)
    @JdbcType(VarcharJdbcType.class)
    private Class<?> tokenClass;

    public OAuth2TokenDesc(@NonNull String authorizationId,@NonNull OAuth2TokenType oAuth2TokenType) {
        this.authorizationId = authorizationId;
        if(oAuth2TokenType.equals(OAuth2TokenType.REFRESH_TOKEN)){
            this.tokenClass = OAuth2RefreshToken.class;
        } else if (oAuth2TokenType.equals(OAuth2TokenType.ACCESS_TOKEN)) {
            this.tokenClass = OAuth2AccessToken.class;
        }
    }
}
