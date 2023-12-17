package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel.REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel.REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD;

class ClientSecretStdSerializer extends AbstractObjectStdSerializer<ClientSecret> {

    ClientSecretStdSerializer() {
        super(ClientSecret.class);
    }

    @Override
    protected void serializeObject(ClientSecret clientSecret, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD, clientSecret.getValue(), jsonGenerator);
        writeField(REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD, clientSecret.getExpiresDateTime(), jsonGenerator, false);
    }
}
