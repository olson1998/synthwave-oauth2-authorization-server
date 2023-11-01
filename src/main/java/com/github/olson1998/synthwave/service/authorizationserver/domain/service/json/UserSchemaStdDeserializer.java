package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliationEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserSchemaDTO.*;

class UserSchemaStdDeserializer extends AbstractObjectStdDeserializer<UserSchema> {

    UserSchemaStdDeserializer() {
        super(UserSchema.class);
    }

    @Override
    protected UserSchema deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var user= readJsonProperty(USER_SCHEMA_USER_JSON_FIELD, objectNode, objectCodec, UserEntity.class, true);
        var password = readJsonProperty(USER_SCHEMA_PASSWORD_JSON_FIELD, objectNode, objectCodec, PasswordEntity.class, true);
        var affiliation = readJsonProperty(USER_SCHEMA_AFFILIATION_JSON_FIELD, objectNode, objectCodec, UserAffiliationEntity.class, true);
        return new UserSchemaDTO(
                user,
                affiliation,
                password
        );
    }
}
