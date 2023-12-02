package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.joda.time.MutableDateTime;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RegisteredClientSecretModel.*;

class RegisteredClientSecretStdDeserializer extends AbstractObjectStdDeserializer<RegisteredClientSecret> {

    RegisteredClientSecretStdDeserializer() {
        super(RegisteredClientSecret.class);
    }

    @Override
    protected RegisteredClientSecret deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var clientId = readJsonProperty(REGISTERED_CLIENT_SECRET_CLIENT_ID_JSON_FIELD, objectNode, objectCodec, TSID.class);
        var value = readJsonProperty(REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var expireAt = readJsonProperty(REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD, objectNode, objectCodec, MutableDateTime.class);
        return new RegisteredClientSecretModel(clientId, value, expireAt);
    }
}
