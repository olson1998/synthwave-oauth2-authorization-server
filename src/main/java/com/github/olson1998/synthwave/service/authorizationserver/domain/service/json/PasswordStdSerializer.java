package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Password;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.PasswordJson.*;

class PasswordStdSerializer extends AbstractObjectStdSerializer<Password> {

    protected PasswordStdSerializer() {
        super(Password.class);
    }

    @Override
    protected void serializeObject(Password password, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(PASSWORD_ID_JSON_FIELD, password.getId(), jsonGenerator);
        writeField(PASSWORD_USER_ID_JSON_FIELD, password.getUserId(), jsonGenerator);
        writeField(PASSWORD_EXPIRE_PERIOD_JSON_FILED, password.getOptionalExpirePeriod(), jsonGenerator, false);
        writeField(PASSWORD_LATEST_VER_JSON_FIELD, password.getLatestVersion(), jsonGenerator);
    }

    private boolean isNotLatestVer(){
        return false;
    }

}
