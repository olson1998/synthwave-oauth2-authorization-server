package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAccountLock;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserAccountLockDTO.*;

class UserAccountLockStdSerializer extends AbstractObjectStdSerializer<UserAccountLock> {

    UserAccountLockStdSerializer() {
        super(UserAccountLock.class);
    }

    @Override
    protected void serializeObject(UserAccountLock userAccountLock, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_BAN_ID_JSON_FIELD, userAccountLock.getId(), jsonGenerator);
        writeField(USER_BAN_USER_ID_JSON_FIELD, userAccountLock.getUserId(), jsonGenerator);
    }
}
