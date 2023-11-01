package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserEntityDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserEntity;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserEntityDTO.*;

class UserEntityStdDeserializer extends AbstractObjectStdDeserializer<UserEntity> {

    UserEntityStdDeserializer() {
        super(UserEntity.class);
    }

    @Override
    protected UserEntity deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var id = readJsonProperty(USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        var username = readJsonProperty(USER_NAME_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var enabled = readJsonProperty(USER_ENABLED_JSON_FIELD, objectNode, objectCodec, Boolean.class);
        var expPeriod = readJsonProperty(USER_EXP_PERIOD_JSON_FIELD, objectNode, objectCodec, Period.class);
        return new UserEntityDTO(id, username, enabled, expPeriod);
    }
}
