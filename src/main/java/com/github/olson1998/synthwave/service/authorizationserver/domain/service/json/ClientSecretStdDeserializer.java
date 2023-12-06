package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import org.joda.time.MutableDateTime;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel.REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel.REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD;

class ClientSecretStdDeserializer extends AbstractObjectStdDeserializer<ClientSecret> {

    ClientSecretStdDeserializer() {
        super(ClientSecret.class);
    }

    @Override
    protected ClientSecret deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var value = readJsonProperty(REGISTERED_CLIENT_SECRET_VALUE_JSON_FIELD, objectNode, objectCodec, String.class, true);
        var expireAt = readJsonProperty(REGISTERED_CLIENT_SECRET_EXP_DATE_JSON_FIELD, objectNode, objectCodec, MutableDateTime.class);
        return new ClientSecretModel(value, expireAt);
    }
}
