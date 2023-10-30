package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.OAuth2TokenDesc;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "O2TOKN")
public class OAuth2TokenData implements TokenProperties, Persistable<OAuth2TokenDesc> {

    @EmbeddedId
    private OAuth2TokenDesc description;

    @Column(name = "TKNVAL",nullable = false)
    private String value;

    @Column(name = "TKNIAT")
    private Instant issuedAt;

    @Column(name = "TKNEAT")
    private Instant expiresAt;

    @Column(name = "ADPROP")
    private String additionalPropertiesJSON;

    public OAuth2TokenData(@NonNull TokenProperties tokenProperties) {
        this.description = new OAuth2TokenDesc(
                tokenProperties.getAuthorizationId(),
                tokenProperties.getTokenClass()
        );
        this.value = tokenProperties.getValue();
        this.issuedAt = tokenProperties.getIssuedAt();
        this.expiresAt = tokenProperties.getExpiresAt();
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

    @Override
    public Optional<String> getOptionalAdditionalPropertiesJSON() {
        return Optional.ofNullable(additionalPropertiesJSON);
    }

    @Override
    public OAuth2TokenDesc getId() {
        return description;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
