package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.UserSavingRequestImpl;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.UserSavingRequestImpl.USER_SAVING_REQUEST_PASSWORD_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.UserSavingRequestImpl.USER_SAVING_REQUEST_USER_JSON_FIELD;

class UserSavingRequestStdDeserializer extends AbstractObjectStdDeserializer<UserSavingRequest> {

    UserSavingRequestStdDeserializer() {
        super(UserSavingRequest.class);
    }

    @Override
    protected UserSavingRequest deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var user = readJsonProperty(
                USER_SAVING_REQUEST_USER_JSON_FIELD,
                objectNode,
                objectCodec,
                UserProperties.class
        );
        var password = readJsonProperty(
                USER_SAVING_REQUEST_PASSWORD_JSON_FIELD,
                objectNode,
                objectCodec,
                Password.class
        );
        var affiliation = readJsonProperty(
                USER_SAVING_REQUEST_PASSWORD_JSON_FIELD,
                objectNode,
                objectCodec,
                Affiliation.class
        );
        return new UserSavingRequestImpl(user, password, affiliation);
    }
}
