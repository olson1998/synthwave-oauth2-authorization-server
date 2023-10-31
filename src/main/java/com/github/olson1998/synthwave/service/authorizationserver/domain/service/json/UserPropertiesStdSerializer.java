package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserProperties;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserPropertiesDTO.*;

class UserPropertiesStdSerializer extends AbstractObjectStdSerializer<UserProperties> {

    UserPropertiesStdSerializer() {
        super(UserProperties.class);
    }

    @Override
    protected void serializeObject(UserProperties userProperties, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_ID_JSON_FIELD, userProperties.getId(), jsonGenerator);
        writeField(USER_NAME_JSON_FIELD, userProperties.getUsername(), jsonGenerator);
        writeField(USER_ENABLED_JSON_FIELD, userProperties.isEnabled(), jsonGenerator);
        writeField(USER_EXP_PERIOD_JSON_FIELD, userProperties.getExpirePeriod(), jsonGenerator, false);
    }
}
