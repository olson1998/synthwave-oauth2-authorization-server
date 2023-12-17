package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserPassword;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_EXPIRE_DATE_TIME_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PasswordModel.PASSWORD_VALUE_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.UserPasswordModel.PASSWORD_USER_ID_JSON_FIELD;

public class UserPasswordStdSerializer extends AbstractObjectStdSerializer<UserPassword> {

    UserPasswordStdSerializer() {
        super(UserPassword.class);
    }

    @Override
    protected void serializeObject(UserPassword userPassword, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(PASSWORD_USER_ID_JSON_FIELD, userPassword.getUserId(), jsonGenerator);
        writeField(PASSWORD_VALUE_JSON_FILED, userPassword.getValue(), jsonGenerator);
        writeField(PASSWORD_EXPIRE_DATE_TIME_JSON_FILED, userPassword.getExpireDateTime(), jsonGenerator, false);
    }
}
