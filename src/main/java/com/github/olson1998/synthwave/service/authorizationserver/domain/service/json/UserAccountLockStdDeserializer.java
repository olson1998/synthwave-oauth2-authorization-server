package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserAccountLockModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAccountLock;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserAccountLockModel.USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD;

class UserAccountLockStdDeserializer extends AbstractObjectStdDeserializer<UserAccountLock> {

    UserAccountLockStdDeserializer() {
        super(UserAccountLock.class);
    }

    @Override
    protected UserAccountLock deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var userId = readJsonProperty(USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class, true);
        return new UserAccountLockModel(userId);
    }
}
