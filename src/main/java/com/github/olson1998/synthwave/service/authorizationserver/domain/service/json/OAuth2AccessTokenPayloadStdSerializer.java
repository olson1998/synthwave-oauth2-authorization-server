package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2AccessTokenPayload;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2TokenMapperImpl.*;

class OAuth2AccessTokenPayloadStdSerializer extends AbstractObjectStdSerializer<OAuth2AccessTokenPayload> {


    protected OAuth2AccessTokenPayloadStdSerializer() {
        super(OAuth2AccessTokenPayload.class);
    }

    @Override
    protected void serializeObject(OAuth2AccessTokenPayload oAuth2AccessTokenPayload, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(ACCESS_TOKEN_TYPE_PROP, oAuth2AccessTokenPayload.tokenType(), jsonGenerator);
        writeField(ACCESS_TOKEN_SCOPES_PROP, oAuth2AccessTokenPayload.scopes(), jsonGenerator);
    }
}
