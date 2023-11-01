package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

class UserPropertiesStdSerializer extends AbstractObjectStdSerializer<UserProperties> {


    UserPropertiesStdSerializer() {
        super(UserProperties.class);
    }

    @Override
    protected void serializeObject(UserProperties userProperties, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
