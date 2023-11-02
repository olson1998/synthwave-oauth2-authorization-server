package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import org.joda.time.Period;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO.USER_EXP_PERIOD_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserPropertiesDTO.USER_NAME_JSON_FIELD;

class UserPropertiesStdDeserializer extends AbstractObjectStdDeserializer<UserProperties> {

    UserPropertiesStdDeserializer() {
        super(UserProperties.class);
    }

    @Override
    protected UserProperties deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var username = readJsonProperty(USER_NAME_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var userExpPrd = readJsonProperty(USER_EXP_PERIOD_JSON_FIELD, objectNode, objectCodec, Period.class);
        return new UserPropertiesDTO(username, userExpPrd);
    }
}
