package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityModel.*;

class PasswordEntityStdDeserializer extends AbstractObjectStdDeserializer<PasswordEntity> {

    PasswordEntityStdDeserializer() {
        super(PasswordEntity.class);
    }

    @Override
    protected PasswordEntity deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        var id = readJsonProperty(PASSWORD_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        var userId= readJsonProperty(PASSWORD_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class, true);
        var value = readJsonProperty(PASSWORD_VALUE_JSON_FILED, objectNode, objectCodec, String.class, true);
        var expirePeriod = readJsonProperty(PASSWORD_EXPIRE_PERIOD_JSON_FILED, objectNode, objectCodec, Period.class);
        var latestVersion = readJsonProperty(PASSWORD_LATEST_VER_JSON_FIELD, objectNode, objectCodec, Boolean.class);
        return new PasswordEntityModel(
                id,
                userId,
                value,
                latestVersion,
                expirePeriod
        );
    }
}
