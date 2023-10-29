package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2AuthorizationPropertiesImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2AuthorizationMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2TokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class OAuth2AuthorizationMapperImpl implements OAuth2AuthorizationMapper {

    private final ObjectMapper objectMapper;

    private final OAuth2TokenMapper oAuth2TokenMapper;

    private static final Set<Class<? extends OAuth2Token>> OAUTH2_TOKENS = Set.of(
            Jwt.class,
            OAuth2AccessToken.class,
            OAuth2RefreshToken.class,
            OAuth2AuthorizationCode.class,
            OAuth2DeviceCode.class,
            OAuth2UserCode.class,
            OidcIdToken.class
    );

    @Override
    public AuthorizationProperties map(OAuth2Authorization oAuth2Authorization) {
        var attributesJSON = Optional.ofNullable(oAuth2Authorization.getAttributes())
                .map(this::serializePOJO)
                .orElse(null);
        return new OAuth2AuthorizationPropertiesImpl(
                oAuth2Authorization.getId(),
                oAuth2Authorization.getRegisteredClientId(),
                oAuth2Authorization.getPrincipalName(),
                oAuth2Authorization.getAuthorizationGrantType(),
                attributesJSON
        );
    }

    @Override
    public OAuth2Authorization map(AuthorizationProperties properties, Collection<TokenProperties> tokenPropertiesCollection) {
        var clientId = properties.getRegisteredClientId();
        var registeredClient = RegisteredClient.withId(clientId).build();
        var tokens = oAuth2TokenMapper.read(tokenPropertiesCollection);
        var oauth2Authorization = OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(properties.getId())
                .principalName(properties.getPrincipal())
                .authorizationGrantType(properties.getAuthorizationGrantType());
        tokens.resolveOptionalAccessToken().ifPresent(oauth2Authorization::accessToken);
        tokens.resolveOptionalRefreshToken().ifPresent(oauth2Authorization::refreshToken);
        return oauth2Authorization.build();
    }

    @Override
    public Collection<OAuth2Token> resolveAvailableTokens(OAuth2Authorization oAuth2Authorization){
        return OAUTH2_TOKENS.stream()
                .map(clazz -> resolveAvailableToken(oAuth2Authorization, clazz))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(OAuth2Authorization.Token::getToken)
                .map(OAuth2Token.class::cast)
                .toList();
    }

    private <T extends OAuth2Token> Optional<OAuth2Authorization.Token<T>> resolveAvailableToken(OAuth2Authorization oAuth2Authorization, Class<T> tokenClass){
        return Optional.ofNullable(oAuth2Authorization.getToken(tokenClass));
    }

    @SneakyThrows
    private String serializePOJO(Object pojo){
        return objectMapper.writeValueAsString(pojo);
    }
}
