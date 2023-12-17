package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RedirectEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RedirectEntityModel.REDIRECT_ID_JSON_FIELD;

class RedirectEntityStdDeserializer extends AbstractObjectStdDeserializer<RedirectEntity> {

    private final RedirectStdDeserializer redirectStdDeserializer;

    RedirectEntityStdDeserializer(RedirectStdDeserializer redirectStdDeserializer) {
        super(RedirectEntity.class);
        this.redirectStdDeserializer = redirectStdDeserializer;
    }

    @Override
    protected RedirectEntity deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var id = readJsonProperty(REDIRECT_ID_JSON_FIELD, objectNode, objectCodec, TSID.class, true);
        var redirect = redirectStdDeserializer.deserializeObjectNode(objectNode, objectCodec, p, ctxt);
        return new RedirectEntityModel(id, redirect);
    }
}
