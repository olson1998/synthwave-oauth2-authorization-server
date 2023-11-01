package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

class UserPropertiesStdDeserializer extends AbstractObjectStdDeserializer<UserProperties> {

    UserPropertiesStdDeserializer() {
        super(UserProperties.class);
    }

    @Override
    protected UserProperties deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        return null;
    }
}
