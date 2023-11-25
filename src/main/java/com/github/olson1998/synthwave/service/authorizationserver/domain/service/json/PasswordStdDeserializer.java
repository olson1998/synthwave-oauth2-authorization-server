package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_EXPIRE_PERIOD_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_VALUE_JSON_FILED;

class PasswordStdDeserializer extends AbstractObjectStdDeserializer<Password> {

    PasswordStdDeserializer() {
        super(Password.class);
    }

    @Override
    protected Password deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var value= readJsonProperty(PASSWORD_VALUE_JSON_FILED, objectNode, objectCodec, String.class, true);
        var userId = readJsonProperty("user_id", objectNode, objectCodec, TSID.class);
        var latestVersion = readJsonProperty("latest", objectNode, objectCodec, Boolean.class);
        var expirePeriod = readJsonProperty(PASSWORD_EXPIRE_PERIOD_JSON_FILED, objectNode, objectCodec, Period.class);
        return new PasswordModel(value, userId, latestVersion, expirePeriod);
    }
}
