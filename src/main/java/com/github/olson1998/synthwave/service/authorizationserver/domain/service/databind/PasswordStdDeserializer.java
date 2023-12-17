package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import org.joda.time.MutableDateTime;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_EXPIRE_DATE_TIME_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_VALUE_JSON_FILED;

class PasswordStdDeserializer extends AbstractObjectStdDeserializer<Password> {

    PasswordStdDeserializer() {
        super(Password.class);
    }

    @Override
    protected Password deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var value= readJsonProperty(PASSWORD_VALUE_JSON_FILED, objectNode, objectCodec, String.class, true);
        var expireDate = readJsonProperty(PASSWORD_EXPIRE_DATE_TIME_JSON_FILED, objectNode, objectCodec, MutableDateTime.class);
        return new PasswordModel(value, expireDate);
    }
}
