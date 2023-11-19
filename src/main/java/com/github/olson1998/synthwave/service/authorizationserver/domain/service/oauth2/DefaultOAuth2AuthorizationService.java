package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2AuthorizationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class DefaultOAuth2AuthorizationService implements OAuth2AuthorizationRepository {

    private final Set<OAuth2Authorization> oAuth2Authorizations = new HashSet<>();

    @Override
    public void save(@NonNull OAuth2Authorization authorization) {
        log.debug("Caching OAuth2 authorization: {}", authorization);
        oAuth2Authorizations.add(authorization);
    }

    @Override
    public void remove(@NonNull OAuth2Authorization authorization) {
        log.debug("Removing OAuth2 authorization: {}", authorization);
        oAuth2Authorizations.stream()
                .filter(oAuth2Authorization -> oAuth2Authorization.getId().equals(authorization.getId()))
                .findFirst()
                .ifPresent(oAuth2Authorizations::remove);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        log.debug("Searching for OAuth2 authorization: {}", id);
        return oAuth2Authorizations.stream()
                .filter(oAuth2Authorization -> oAuth2Authorization.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        var tokenTypeValue = Optional.ofNullable(tokenType).map(OAuth2TokenType::getValue).orElse("{?}");
        log.debug("Searching for OAuth2 authorization with type: {}", tokenTypeValue);
        return oAuth2Authorizations.stream()
                .filter(oAuth2Authorization -> isSameTokenAndType(oAuth2Authorization, token, tokenType))
                .findAny()
                .orElse(null);
    }

    private boolean isSameTokenAndType(OAuth2Authorization oAuth2Authorization, String token, OAuth2TokenType tokenType){
        if(tokenType == null){
            return oAuth2Authorization.getToken(token) != null;
        } else if(tokenType.equals(OAuth2TokenType.ACCESS_TOKEN)){
            return Optional.ofNullable(oAuth2Authorization.getAccessToken())
                    .map(OAuth2Authorization.Token::getToken)
                    .map(OAuth2AccessToken::getTokenValue)
                    .stream()
                    .anyMatch(tokenValue -> tokenValue.equals(token));
        } else if (tokenType.equals(OAuth2TokenType.REFRESH_TOKEN)) {
            return Optional.ofNullable(oAuth2Authorization.getRefreshToken())
                    .map(OAuth2Authorization.Token::getToken)
                    .map(OAuth2RefreshToken::getTokenValue)
                    .stream()
                    .anyMatch(tokenValue -> tokenValue.equals(token));
        } else {
            return oAuth2Authorization.getToken(token) != null;
        }
    }
}
