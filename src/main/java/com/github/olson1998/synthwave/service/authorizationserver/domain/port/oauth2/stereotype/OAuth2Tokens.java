package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;

import java.util.Collection;
import java.util.Optional;

public interface OAuth2Tokens {

    Collection<OAuth2Token> collection();

    Optional<OAuth2RefreshToken> resolveOptionalRefreshToken();

    Optional<OAuth2AccessToken> resolveOptionalAccessToken();

}
