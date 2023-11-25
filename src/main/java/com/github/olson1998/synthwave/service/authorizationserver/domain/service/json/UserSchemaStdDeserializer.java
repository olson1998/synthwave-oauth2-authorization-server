package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaModel.*;

class UserSchemaStdDeserializer extends AbstractObjectStdDeserializer<UserSchema> {

    UserSchemaStdDeserializer() {
        super(UserSchema.class);
    }

    @Override
    protected UserSchema deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var userProps = readJsonProperty(USER_SCHEMA_USER_JSON_FIELD, objectNode, objectCodec, UserProperties.class, true);
        var passwordProps = readJsonProperty(USER_SCHEMA_PASSWORD_JSON_FIELD, objectNode, objectCodec, Password.class, true);
        var affiliationProps = readJsonProperty(USER_SCHEMA_AFFILIATION_JSON_FIELD, objectNode, objectCodec, Affiliation.class, true);
        return new UserSchemaModel(userProps, passwordProps, affiliationProps);
    }

}
