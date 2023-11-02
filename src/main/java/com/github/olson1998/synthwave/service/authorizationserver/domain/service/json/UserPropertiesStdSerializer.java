package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO.USER_EXP_PERIOD_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO.USER_NAME_JSON_FIELD;

class UserPropertiesStdSerializer extends AbstractObjectStdSerializer<UserProperties> {


    UserPropertiesStdSerializer() {
        super(UserProperties.class);
    }

    @Override
    protected void serializeObject(UserProperties userProperties, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_NAME_JSON_FIELD, userProperties.getUsername(), jsonGenerator);
        writeField(USER_EXP_PERIOD_JSON_FIELD, userProperties.getExpirePeriod(), jsonGenerator, false);
    }
}
