package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO.*;

class PasswordStdSerializer extends AbstractObjectStdSerializer<Password> {

    PasswordStdSerializer() {
        super(Password.class);
    }

    @Override
    protected void serializeObject(Password password, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(PASSWORD_VALUE_JSON_FILED, password.getValue(), jsonGenerator);
        writeField(PASSWORD_EXPIRE_PERIOD_JSON_FILED, password.getOptionalExpirePeriod(), jsonGenerator, false);
    }
}
