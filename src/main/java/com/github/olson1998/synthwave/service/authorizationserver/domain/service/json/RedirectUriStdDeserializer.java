package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson.REDIRECT_URI_TYPE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson.REDIRECT_URI_VALUE_JSON_FIELD;

class RedirectUriStdDeserializer extends AbstractObjectStdDeserializer<RedirectUri> {

    RedirectUriStdDeserializer() {
        super(RedirectUri.class);
    }

    @Override
    protected RedirectUri deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var type = readJsonProperty(
                REDIRECT_URI_TYPE_JSON_FIELD,
                objectNode,
                objectCodec,
                RedirectUriJson.Type.class,
                true
        );
        var uri = readJsonProperty(
                REDIRECT_URI_VALUE_JSON_FIELD,
                objectNode,
                objectCodec,
                String.class,
                true
        );
        return new RedirectUriJson(type, uri);
    }
}
