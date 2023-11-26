package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.json.fields.TokenSettingsJsonFields.*;

class TokenSettingsStdSerializer extends AbstractObjectStdSerializer<TokenSettings> {

    TokenSettingsStdSerializer() {
        super(TokenSettings.class);
    }

    @Override
    protected void serializeObject(TokenSettings tokenSettings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(TOKEN_SETTINGS_SIGNATURE_ALG, tokenSettings.getIdTokenSignatureAlgorithm(), jsonGenerator);
        writeField(TOKEN_SETTINGS_ACCESS_TOKEN_FORMAT, tokenSettings.getAccessTokenFormat(), jsonGenerator);
        writeField(REUSE_REFRESH_TOKEN, tokenSettings.isReuseRefreshTokens(), jsonGenerator);
        writeField(ACCESS_TOKEN_TIME_TO_LIVE, tokenSettings.getAccessTokenTimeToLive(), jsonGenerator);
        writeField(REFRESH_TOKEN_TIME_TO_LIVE, tokenSettings.getRefreshTokenTimeToLive(), jsonGenerator);
        writeField(DEVICE_CODE_TIME_TO_LIVE, tokenSettings.getDeviceCodeTimeToLive(), jsonGenerator);
        writeField(AUTHORIZATION_CODE_TIME_TO_LIVE, tokenSettings.getAuthorizationCodeTimeToLive(), jsonGenerator);
    }
}
