package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserEntityModel.*;

class UserEntityStdSerializer extends AbstractObjectStdSerializer<UserEntity> {

    UserEntityStdSerializer() {
        super(UserEntity.class);
    }

    @Override
    protected void serializeObject(UserEntity userEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_ID_JSON_FIELD, userEntity.getId(), jsonGenerator);
        writeField(USER_NAME_JSON_FIELD, userEntity.getUsername(), jsonGenerator);
        writeField(USER_ENABLED_JSON_FIELD, userEntity.getEnabled(), jsonGenerator);
        writeField(USER_EXP_PERIOD_JSON_FIELD, userEntity.getExpireDateTime(), jsonGenerator, false);
    }
}
