package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import com.github.olson1998.synthwave.support.jackson.exception.IORuntimeException;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.io.IOException;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields.TokenSettingsJsonFields.REFERENCE;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields.TokenSettingsJsonFields.SELF_CONTAINED;

class OAuth2TokenFormatStdDeserializer extends AbstractTextStdDeserializer<OAuth2TokenFormat> {

    OAuth2TokenFormatStdDeserializer() {
        super(OAuth2TokenFormat.class);
    }

    @Override
    protected OAuth2TokenFormat deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        return findTokenFormat(textValue)
                .orElseThrow(()-> new IORuntimeException(new IOException("Unknown OAuth2 token format: " + textValue)));
    }

    private Optional<OAuth2TokenFormat> findTokenFormat(String textValue){
        if(textValue.equals(SELF_CONTAINED)){
            return Optional.of(OAuth2TokenFormat.SELF_CONTAINED);
        } else if (textValue.equals(REFERENCE)) {
            return Optional.of(OAuth2TokenFormat.REFERENCE);
        }else {
            return Optional.empty();
        }
    }
}
