package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2AccessTokenPayload;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2TokenPropertiesImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2TokensImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2TokenMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Tokens;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OAuth2TokenMapperImpl implements OAuth2TokenMapper {

    public static final String JWT_HEADERS_PROP = "headers";

    public static final String JWT_CLAIMS_PROP = "claims";

    public static final String ACCESS_TOKEN_TYPE_PROP = "type";

    public static final String ACCESS_TOKEN_SCOPES_PROP = "scopes";

    public static final String OIDC_ID_TOKEN_CLAIMS_PROPS = "claims";

    private final ObjectMapper objectMapper;

    @Override
    public Set<TokenProperties> map(@NonNull String authorizationId, @NonNull Collection<OAuth2Token> tokens) {
        return tokens.stream()
                .map(oAuth2Token -> mapToken(authorizationId, oAuth2Token))
                .collect(Collectors.toSet());
    }

    @Override
    public OAuth2Tokens read(@NonNull Collection<TokenProperties> tokenPropertiesCollection) {
        return new OAuth2TokensImpl(tokenPropertiesCollection.stream()
                .map(this::read)
                .toList());
    }

    @Override
    public OAuth2Token read(@NonNull TokenProperties tokenProperties) {
        var clazz = tokenProperties.getTokenClass();
        if(clazz.equals(Jwt.class)){
            var jwtPropsJson = tokenProperties.getOptionalAdditionalPropertiesJSON()
                    .orElseThrow();
            var jwtProps = deserializeJSON(jwtPropsJson, new TypeReference<Map<String, Map<String, Object>>>() {
            });
            return new Jwt(
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt(),
                    jwtProps.get(JWT_HEADERS_PROP),
                    jwtProps.get(JWT_CLAIMS_PROP)
            );
        } else if (clazz.equals(OAuth2AccessToken.class)) {
            var jwtPropsJson = tokenProperties.getOptionalAdditionalPropertiesJSON()
                    .orElseThrow();
            var payload = deserializeJSON(jwtPropsJson, OAuth2AccessTokenPayload.class);
            return new OAuth2AccessToken(
                    payload.tokenType(),
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt(),
                    payload.scopes()
            );
        } else if (clazz.equals(OAuth2RefreshToken.class)) {
            return new OAuth2RefreshToken(
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt()
            );
        } else if (clazz.equals(OAuth2AuthorizationCode.class)) {
            return new OAuth2AuthorizationCode(
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt()
            );
        } else if (clazz.equals(OAuth2DeviceCode.class)) {
            return new OAuth2DeviceCode(
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt()
            );
        } else if (clazz.equals(OAuth2UserCode.class)) {
            return new OAuth2UserCode(
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt()
            );
        } else if (clazz.equals(OidcIdToken.class)) {
            var jwtPropsJson = tokenProperties.getOptionalAdditionalPropertiesJSON()
                    .orElseThrow();
            var claims = deserializeJSON(jwtPropsJson, new TypeReference<Map<String, Map<String, Object>>>() {
            });
            return new OidcIdToken(
                    tokenProperties.getValue(),
                    tokenProperties.getIssuedAt(),
                    tokenProperties.getExpiresAt(),
                    claims.get(OIDC_ID_TOKEN_CLAIMS_PROPS)
            );
        }else {
            throw new RuntimeException();
        }
    }

    private TokenProperties mapToken(String authorizationId, OAuth2Token oAuth2Token){
        Object additionalProps = null;
        if(oAuth2Token instanceof Jwt jwt){
            var props = new HashMap<String, Map<String, Object>>();
            props.put(JWT_HEADERS_PROP, jwt.getHeaders());
            props.put(JWT_CLAIMS_PROP, jwt.getClaims());
            additionalProps = props;
        } else if (oAuth2Token instanceof OAuth2AccessToken oAuth2AccessToken) {
            additionalProps = new OAuth2AccessTokenPayload(
                    oAuth2AccessToken.getTokenType(),
                    oAuth2AccessToken.getScopes()
            );
        } else if (oAuth2Token instanceof OidcIdToken oidcIdToken) {
            additionalProps = Collections.singletonMap(OIDC_ID_TOKEN_CLAIMS_PROPS, oidcIdToken.getClaims());
        }
        var additionalPropsJson =Optional.ofNullable(additionalProps)
                .map(this::serializePOJO)
                .orElse(null);
        return new OAuth2TokenPropertiesImpl(
                authorizationId,
                oAuth2Token,
                additionalPropsJson
        );
    }

    @SneakyThrows
    private String serializePOJO(Object obj){
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    private <T> T deserializeJSON(String json, TypeReference<T> typeReference){
        return objectMapper.readValue(json, typeReference);
    }

    @SneakyThrows
    private <T> T deserializeJSON(String json, Class<T> typeReference){
        return objectMapper.readValue(json, typeReference);
    }
}
