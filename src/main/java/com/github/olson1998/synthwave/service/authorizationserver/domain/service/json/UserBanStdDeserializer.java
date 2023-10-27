package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserBanJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserBan;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.joda.time.MutableDateTime;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserBanJson.*;

class UserBanStdDeserializer extends AbstractObjectStdDeserializer<UserBan> {

    UserBanStdDeserializer() {
        super(UserBan.class);
    }

    @Override
    protected UserBan deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var id = readJsonProperty(USER_BAN_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        var userId= readJsonProperty(USER_BAN_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        var timestamp = readJsonProperty(USER_BAN_TIMESTAMP_FIELD, objectNode, objectCodec, MutableDateTime.class);
        return new UserBanJson(id, userId, timestamp);
    }
}
