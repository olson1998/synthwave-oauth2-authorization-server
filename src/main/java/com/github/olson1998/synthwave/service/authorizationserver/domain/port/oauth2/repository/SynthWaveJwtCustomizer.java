package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

public interface SynthWaveJwtCustomizer extends OAuth2TokenCustomizer<JwtEncodingContext> {
}
