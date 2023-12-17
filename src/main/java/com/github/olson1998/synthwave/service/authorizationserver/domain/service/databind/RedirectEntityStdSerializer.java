package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.RedirectEntityModel.REDIRECT_ID_JSON_FIELD;

class RedirectEntityStdSerializer extends AbstractObjectStdSerializer<RedirectEntity> {

    protected final RedirectStdSerializer redirectStdSerializer;

    RedirectEntityStdSerializer(RedirectStdSerializer redirectStdSerializer) {
        super(RedirectEntity.class);
        this.redirectStdSerializer = redirectStdSerializer;
    }

    @Override
    protected void serializeObject(RedirectEntity redirectEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(REDIRECT_ID_JSON_FIELD, redirectEntity.getId(), jsonGenerator);
        redirectStdSerializer.serializeObject(redirectEntity, jsonGenerator, serializerProvider);
    }
}
