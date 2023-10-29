package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import java.time.Instant;

public interface OAuth2TokenProperties {

    String getAuthorizationId();

    Class<?> getTokenClass();

    Instant getIssuedAt();

    Instant getExpiresAt();
}
