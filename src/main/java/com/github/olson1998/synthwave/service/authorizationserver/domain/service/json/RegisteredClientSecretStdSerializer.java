package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel.*;

class RegisteredClientSecretStdSerializer extends AbstractObjectStdSerializer<RegisteredClientSecret> {

    RegisteredClientSecretStdSerializer() {
        super(RegisteredClientSecret.class);
    }

    @Override
    protected void serializeObject(RegisteredClientSecret registeredClientSecret, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(REGISTERED_CLIENT_SECRET_CLIENT_ID_JSON_FIELD, registeredClientSecret.getRegisteredClientId(), jsonGenerator, false);
        writeField(REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD, registeredClientSecret.getValue(), jsonGenerator);
        writeField(REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD, registeredClientSecret.getExpiresDateTime(), jsonGenerator, false);
    }
}
