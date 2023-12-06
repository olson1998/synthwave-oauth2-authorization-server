package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.PasswordEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityModel.*;

class PasswordEntityStdSerializer extends AbstractObjectStdSerializer<PasswordEntity> {

    protected PasswordEntityStdSerializer() {
        super(PasswordEntity.class);
    }

    @Override
    protected void serializeObject(PasswordEntity passwordEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(PASSWORD_ID_JSON_FIELD, passwordEntity.getId(), jsonGenerator);
        writeField(PASSWORD_USER_ID_JSON_FIELD, passwordEntity.getUserId(), jsonGenerator);
        writeField(PASSWORD_VALUE_JSON_FILED, passwordEntity.getValue(), jsonGenerator);
        writeField(PASSWORD_EXPIRE_DATE_TIME_JSON_FILED, passwordEntity.getExpireDateTime(), jsonGenerator, false);
    }

}
