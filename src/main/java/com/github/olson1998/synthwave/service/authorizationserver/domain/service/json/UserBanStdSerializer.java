package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserBan;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.UserBanJson.*;

class UserBanStdSerializer extends AbstractObjectStdSerializer<UserBan> {

    UserBanStdSerializer() {
        super(UserBan.class);
    }

    @Override
    protected void serializeObject(UserBan userBan, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(USER_BAN_ID_JSON_FIELD, userBan.getId(), jsonGenerator);
        writeField(USER_BAN_USER_ID_JSON_FIELD, userBan.getUserId(), jsonGenerator);
        writeField(USER_BAN_TIMESTAMP_FIELD, userBan.getTimestamp(), jsonGenerator);
    }
}
