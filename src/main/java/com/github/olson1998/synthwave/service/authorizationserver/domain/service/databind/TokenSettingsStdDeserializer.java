package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields.TokenSettingsJsonFields.*;

class TokenSettingsStdDeserializer extends AbstractObjectStdDeserializer<TokenSettings> {

    TokenSettingsStdDeserializer() {
        super(TokenSettings.class);
    }

    @Override
    protected TokenSettings deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var tokenSettingsBuilder = TokenSettings.builder();
        var signatureAlgorithm = readJsonProperty(TOKEN_SETTINGS_SIGNATURE_ALG, objectNode, objectCodec, SignatureAlgorithm.class);
        var oauth2AccessTokenFormat = readJsonProperty(TOKEN_SETTINGS_ACCESS_TOKEN_FORMAT, objectNode, objectCodec, OAuth2TokenFormat.class);
        tokenSettingsBuilder.idTokenSignatureAlgorithm(signatureAlgorithm);
        Optional.ofNullable(oauth2AccessTokenFormat)
                .ifPresent(tokenSettingsBuilder::accessTokenFormat);
        Optional.ofNullable(readJsonProperty(REUSE_REFRESH_TOKEN, objectNode, objectCodec, Boolean.class))
                .ifPresent(tokenSettingsBuilder::reuseRefreshTokens);
        Optional.ofNullable(readJsonProperty(ACCESS_TOKEN_TIME_TO_LIVE, objectNode, objectCodec, Duration.class))
                .ifPresent(tokenSettingsBuilder::accessTokenTimeToLive);
        Optional.ofNullable(readJsonProperty(REFRESH_TOKEN_TIME_TO_LIVE, objectNode, objectCodec, Duration.class))
                .ifPresent(tokenSettingsBuilder::refreshTokenTimeToLive);
        Optional.ofNullable(readJsonProperty(DEVICE_CODE_TIME_TO_LIVE, objectNode, objectCodec, Duration.class))
                .ifPresent(tokenSettingsBuilder::deviceCodeTimeToLive);
        Optional.ofNullable(readJsonProperty(AUTHORIZATION_CODE_TIME_TO_LIVE, objectNode, objectCodec, Duration.class))
                .ifPresent(tokenSettingsBuilder::authorizationCodeTimeToLive);
        return tokenSettingsBuilder.build();
    }

}
