package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

class OAuth2TokenFormatStdDeserializer extends AbstractTextStdDeserializer<OAuth2TokenFormat> {

    OAuth2TokenFormatStdDeserializer() {
        super(OAuth2TokenFormat.class);
    }

    @Override
    protected OAuth2TokenFormat deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        return new OAuth2TokenFormat(textValue);
    }
}
