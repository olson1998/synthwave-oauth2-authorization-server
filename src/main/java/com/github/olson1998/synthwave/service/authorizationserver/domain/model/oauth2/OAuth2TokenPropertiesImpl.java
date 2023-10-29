package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import lombok.*;
import org.springframework.security.oauth2.core.OAuth2Token;

import java.time.Instant;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class OAuth2TokenPropertiesImpl implements TokenProperties {

    private final String authorizationId;

    private final String value;

    private final Class<?> tokenClass;

    private final Instant issuedAt;

    private final Instant expiresAt;

    private final String additionalPropertiesJSON;

    public OAuth2TokenPropertiesImpl(@NonNull String authorizationId,@NonNull OAuth2Token oAuth2Token, String additionalPropertiesJSON) {
        this.authorizationId = authorizationId;
        this.value = oAuth2Token.getTokenValue();
        this.tokenClass = oAuth2Token.getClass();
        this.issuedAt = oAuth2Token.getIssuedAt();
        this.expiresAt = oAuth2Token.getExpiresAt();
        this.additionalPropertiesJSON = additionalPropertiesJSON;
    }

    @Override
    public Optional<String> getOptionalAdditionalPropertiesJSON() {
        return Optional.ofNullable(additionalPropertiesJSON);
    }
}
