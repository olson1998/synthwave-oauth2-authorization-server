package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAccountLockEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLockEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAccountLockEntityModel.*;

class UserAccountLockEntityStdDeserializer extends AbstractObjectStdDeserializer<UserAccountLockEntity> {

    UserAccountLockEntityStdDeserializer() {
        super(UserAccountLockEntity.class);
    }

    @Override
    protected UserAccountLockEntity deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var id = readJsonProperty(USER_ACCOUNT_LOCK_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        var userId= readJsonProperty(USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        return new UserAccountLockEntityModel(id, userId);
    }
}