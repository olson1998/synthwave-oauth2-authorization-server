package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import org.joda.time.Period;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO.PASSWORD_EXPIRE_PERIOD_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordDTO.PASSWORD_VALUE_JSON_FILED;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PasswordEntityDTO.PASSWORD_USER_ID_JSON_FIELD;

class PasswordStdDeserializer extends AbstractObjectStdDeserializer<Password> {

    PasswordStdDeserializer() {
        super(Password.class);
    }

    @Override
    protected Password deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var value= readJsonProperty(PASSWORD_VALUE_JSON_FILED, objectNode, objectCodec, String.class, true);
        var expirePeriod = readJsonProperty(PASSWORD_EXPIRE_PERIOD_JSON_FILED, objectNode, objectCodec, Period.class);
        return new PasswordDTO(value, expirePeriod);
    }
}
