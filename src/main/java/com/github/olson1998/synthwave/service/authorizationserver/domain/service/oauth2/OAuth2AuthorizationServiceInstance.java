package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.OAuth2AuthorizationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.OAuth2TokenDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2AuthorizationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2AuthorizationMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2TokenMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthorizationServiceInstance implements OAuth2AuthorizationRepository {

    private final OAuth2TokenMapper oAuth2TokenMapper;

    private final OAuth2AuthorizationMapper oAuth2AuthorizationMapper;

    private final OAuth2TokenDataSourceRepository oAuth2TokenDataSourceRepository;

    private final RedirectURIsDataSourceRepository redirectUrisDataSourceRepository;

    private final OAuth2AuthorizationDataSourceRepository oAuth2AuthorizationDataSourceRepository;

    private final RegisteredClientPropertiesSourceRepository registeredClientPropertiesSourceRepository;

    @Override
    public void save(@NonNull OAuth2Authorization authorization) {
        var authorizationId = authorization.getId();
        var authorizationProperties = oAuth2AuthorizationMapper.map(authorization);
        oAuth2AuthorizationDataSourceRepository.save(authorizationProperties);
        var availableTokens = oAuth2AuthorizationMapper.resolveAvailableTokens(authorization);
        var tokens = oAuth2TokenMapper.map(authorizationId, availableTokens);
        if(!tokens.isEmpty()){
            oAuth2TokenDataSourceRepository.saveAll(tokens);
        }
        log.info("Saved authorization: '{}' with {} tokens", authorizationId, tokens.size());
    }

    @Override
    public void remove(@NonNull OAuth2Authorization authorization) {
        var authorizationId = authorization.getId();
        var authorizationProperties = oAuth2AuthorizationMapper.map(authorization);
        oAuth2AuthorizationDataSourceRepository.delete(authorizationProperties);
        var deletedTokens = oAuth2TokenDataSourceRepository.deleteAllWithAuthorizationId(authorizationId);
        oAuth2AuthorizationDataSourceRepository.delete(authorizationProperties);
        log.info("Deleted authorization: '{}' and deleted {} binding tokens", authorizationId, deletedTokens);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return oAuth2AuthorizationDataSourceRepository.getAuthorizationPropertiesById(id)
                .map(this::mapToOAuth2Authorization)
                .orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Class<?> tokenClass;
        if(tokenType == null){
          return null;
        } else if(tokenType.equals(OAuth2TokenType.REFRESH_TOKEN)){
            tokenClass = OAuth2RefreshToken.class;
        } else if (tokenType.equals(OAuth2TokenType.ACCESS_TOKEN)) {
            tokenClass = OAuth2AccessToken.class;
        }else {
            return null;
        }
        return oAuth2AuthorizationDataSourceRepository.getAuthorizationPropertiesByTokenAndTokenClass(token, tokenClass)
                .map(this::mapToOAuth2Authorization)
                .orElse(null);
    }

    private OAuth2Authorization mapToOAuth2Authorization(AuthorizationProperties authorizationProperties){
        var authorizationId = authorizationProperties.getId();
        var tokens = oAuth2TokenDataSourceRepository.getTokensByAuthorizationId(authorizationId);
        var registeredClientConfig = registeredClientPropertiesSourceRepository.getRegisteredClientConfigByClientId(authorizationProperties.getRegisteredClientId())
                .map(config -> config.withRedirectUris(redirectUrisDataSourceRepository.getAllRedirectUris()))
                .orElseThrow();
        return oAuth2AuthorizationMapper.map(registeredClientConfig, authorizationProperties, tokens);
    }
}
