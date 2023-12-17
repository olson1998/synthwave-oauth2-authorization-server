package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLockEntity;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAccountLockEntityModel.*;

class UserAccountLockEntityStdSerializer extends AbstractObjectStdSerializer<UserAccountLockEntity> {

    UserAccountLockEntityStdSerializer() {
        super(UserAccountLockEntity.class);
    }

    @Override
    protected void serializeObject(UserAccountLockEntity userAccountLockEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_ACCOUNT_LOCK_ID_JSON_FIELD, userAccountLockEntity.getId(), jsonGenerator);
        writeField(USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD, userAccountLockEntity.getUserId(), jsonGenerator);
    }
}
