package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.OAuth2TokenDesc;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.OAuth2TokenProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.core.OAuth2Token;

import java.time.Instant;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class OAuth2TokenData implements OAuth2TokenProperties {

    @EmbeddedId
    private OAuth2TokenDesc description;

    @Column(name = "TKNVAL",nullable = false)
    private String token;

    @Column(name = "TKNIAT")
    private Instant issuedAt;

    @Column(name = "TKNEAT")
    private Instant expiresAt;

    public OAuth2TokenData(@NonNull String authorizationId,@NonNull OAuth2Token oAuth2Token) {
        this.description = new OAuth2TokenDesc(
                authorizationId,
                oAuth2Token.getClass()
        );
        this.token = oAuth2Token.getTokenValue();
        this.issuedAt = oAuth2Token.getIssuedAt();
        this.expiresAt = oAuth2Token.getExpiresAt();
    }

    @Override
    public String getAuthorizationId() {
        return Optional.ofNullable(description)
                .map(OAuth2TokenDesc::getAuthorizationId)
                .orElse(null);
    }

    @Override
    public Class<?> getTokenClass() {
        return Optional.ofNullable(description)
                .map(OAuth2TokenDesc::getTokenClass)
                .orElse(null);
    }
}
