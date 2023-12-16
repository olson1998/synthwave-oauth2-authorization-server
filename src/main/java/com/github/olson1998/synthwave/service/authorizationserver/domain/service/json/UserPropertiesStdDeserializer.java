package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPropertiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;

import java.io.IOException;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPropertiesModel.*;

class UserPropertiesStdDeserializer extends AbstractObjectStdDeserializer<UserProperties> {

    UserPropertiesStdDeserializer() {
        super(UserProperties.class);
    }

    @Override
    protected UserProperties deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var username = readJsonProperty(USER_NAME_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var userExp = readJsonProperty(USER_EXP_PERIOD_JSON_FIELD, objectNode, objectCodec, MutableDateTime.class);
        var enabled = readJsonProperty(USER_ENABLED_JSON_FIELD, objectNode, objectCodec, Boolean.class);
        return new UserPropertiesModel(username, enabled, userExp);
    }
}
