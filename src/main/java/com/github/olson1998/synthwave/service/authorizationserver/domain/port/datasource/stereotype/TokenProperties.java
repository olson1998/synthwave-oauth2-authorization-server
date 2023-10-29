package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import java.time.Instant;
import java.util.Optional;

public interface TokenProperties {

    String getAuthorizationId();

    Class<?> getTokenClass();

    String getValue();

    Instant getIssuedAt();

    Instant getExpiresAt();

    Optional<String> getOptionalAdditionalPropertiesJSON();
}
