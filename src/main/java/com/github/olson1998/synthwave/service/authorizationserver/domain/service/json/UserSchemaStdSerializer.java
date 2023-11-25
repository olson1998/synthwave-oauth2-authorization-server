package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaModel.*;

class UserSchemaStdSerializer extends AbstractObjectStdSerializer<UserSchema> {

    UserSchemaStdSerializer() {
        super(UserSchema.class);
    }

    @Override
    protected void serializeObject(UserSchema userSchema, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_SCHEMA_USER_JSON_FIELD, userSchema.getUser(), jsonGenerator);
        writeField(USER_SCHEMA_PASSWORD_JSON_FIELD, userSchema.getPassword(), jsonGenerator);
        writeField(USER_SCHEMA_AFFILIATION_JSON_FIELD, userSchema.getAffiliation(), jsonGenerator);
    }
}
