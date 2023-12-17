package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserPassword;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.joda.time.MutableDateTime;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_EXPIRE_DATE_TIME_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_VALUE_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPasswordModel.PASSWORD_USER_ID_JSON_FIELD;

class UserPasswordStdDeserializer extends AbstractObjectStdDeserializer<UserPassword> {

    UserPasswordStdDeserializer() {
        super(UserPassword.class);
    }

    @Override
    protected UserPassword deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var userId= readJsonProperty(PASSWORD_USER_ID_JSON_FIELD, objectNode, objectCodec, TSID.class, true);
        var value = readJsonProperty(PASSWORD_VALUE_JSON_FILED, objectNode, objectCodec, String.class, true);
        var expireDate = readJsonProperty(PASSWORD_EXPIRE_DATE_TIME_JSON_FILED, objectNode, objectCodec, MutableDateTime.class);
        return new UserPasswordModel(userId, value, expireDate);
    }
}
