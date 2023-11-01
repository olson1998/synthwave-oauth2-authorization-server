package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.util.Collection;

public interface OAuth2AuthorizationMapper {

    AuthorizationProperties map(OAuth2Authorization oAuth2Authorization);

    OAuth2Authorization map(RegisteredClientConfig registeredClientConfig, AuthorizationProperties properties, Collection<TokenProperties> tokenPropertiesCollection);

    Collection<OAuth2Token> resolveAvailableTokens(OAuth2Authorization oAuth2Authorization);

}
