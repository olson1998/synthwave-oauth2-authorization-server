package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Tokens;
import org.springframework.security.oauth2.core.OAuth2Token;

import java.util.Collection;
import java.util.Set;

public interface OAuth2TokenMapper {

    Set<TokenProperties> map(String authorizationId, Collection<OAuth2Token> tokens);

    OAuth2Token read(TokenProperties tokenProperties);

    OAuth2Tokens read(Collection<TokenProperties> tokenPropertiesCollection);
}
