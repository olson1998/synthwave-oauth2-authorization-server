package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAccountLock;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.UserAccountLockDTO.USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD;

class UserAccountLockStdSerializer extends AbstractObjectStdSerializer<UserAccountLock> {

    UserAccountLockStdSerializer() {
        super(UserAccountLock.class);
    }

    public UserAccountLockStdSerializer(Class<UserAccountLock> t) {
        super(t);
    }

    @Override
    protected void serializeObject(UserAccountLock userAccountLock, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_ACCOUNT_LOCK_USER_ID_JSON_FIELD, userAccountLock.getUserId(), jsonGenerator);
    }
}
