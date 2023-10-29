package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Set;

public record OAuth2AccessTokenPayload(OAuth2AccessToken.TokenType tokenType, Set<String> scopes) {
}
