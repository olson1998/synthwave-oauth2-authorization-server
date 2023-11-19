package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

class TokenSettingsStdDeserializer extends AbstractObjectStdDeserializer<TokenSettings> {

    private static final String SELF_CONTAINED = "SELF_CONTAINED";

    private static final String REFERENCE= "REFERENCE";

    public static final String TOKEN_SETTINGS_SIGNATURE_ALG = "signature_algorithm";

    public static final String TOKEN_SETTINGS_ACCESS_TOKEN_FORMAT = "access_token_format";

    public static final String REUSE_REFRESH_TOKEN = "reuse_refresh_token";

    public static final String ACCESS_TOKEN_TIME_TO_LIVE = "access_token_time_to_live";

    public static final String REFRESH_TOKEN_TIME_TO_LIVE = "refresh_token_time_to_live";

    public static final String DEVICE_CODE_TIME_TO_LIVE = "device_code_time_to_live";

    public static final String AUTHORIZATION_CODE_TIME_TO_LIVE = "authorization_code_time_to_live";

    TokenSettingsStdDeserializer() {
        super(TokenSettings.class);
    }

    @Override
    protected TokenSettings deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var tokenSettingsBuilder = TokenSettings.builder();
        var signatureAlgorithm = readJsonProperty(TOKEN_SETTINGS_SIGNATURE_ALG, objectNode, objectCodec, SignatureAlgorithm.class);
        var oauth2AccessTokenFormat = readJsonProperty(TOKEN_SETTINGS_ACCESS_TOKEN_FORMAT, objectNode, objectCodec, String.class);
        tokenSettingsBuilder.idTokenSignatureAlgorithm(signatureAlgorithm);
        Optional.ofNullable(readJsonProperty(REUSE_REFRESH_TOKEN, objectNode, objectCodec, Boolean.class))
                .ifPresent(tokenSettingsBuilder::reuseRefreshTokens);
        Optional.ofNullable(readJsonProperty(ACCESS_TOKEN_TIME_TO_LIVE, objectNode, objectCodec, Long.class))
                .ifPresent(value -> tokenSettingsBuilder.accessTokenTimeToLive(Duration.ofSeconds(value)));
        Optional.ofNullable(readJsonProperty(REFRESH_TOKEN_TIME_TO_LIVE, objectNode, objectCodec, Long.class))
                .ifPresent(value -> tokenSettingsBuilder.refreshTokenTimeToLive(Duration.ofSeconds(value)));
        Optional.ofNullable(readJsonProperty(DEVICE_CODE_TIME_TO_LIVE, objectNode, objectCodec, Long.class))
                .ifPresent(value -> tokenSettingsBuilder.deviceCodeTimeToLive(Duration.ofSeconds(value)));
        Optional.ofNullable(readJsonProperty(AUTHORIZATION_CODE_TIME_TO_LIVE, objectNode, objectCodec, Long.class))
                .ifPresent(value -> tokenSettingsBuilder.authorizationCodeTimeToLive(Duration.ofSeconds(value)));
        if(oauth2AccessTokenFormat != null){
            if (oauth2AccessTokenFormat.equals(SELF_CONTAINED)) {
                tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED);
            } else if (oauth2AccessTokenFormat.equals(REFERENCE)) {
                tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.REFERENCE);
            }else {
                throw new IOException("Accepted OAuth2 token formats are: SELF_CONTAINED and REFERENCE");
            }
        }
        return tokenSettingsBuilder.build();
    }

}
