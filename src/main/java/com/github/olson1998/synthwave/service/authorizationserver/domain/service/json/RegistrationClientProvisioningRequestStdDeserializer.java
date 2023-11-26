package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.RegistrationClientProvisioningRequestModel.REGISTRATION_CLIENT_REQUEST_REGISTERED_CLIENT_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.RegistrationClientProvisioningRequestModel.REGISTRATION_CLIENT_REQUEST_USER_SAVING_REQUEST_JSON_FIELD;

class RegistrationClientProvisioningRequestStdDeserializer extends AbstractObjectStdDeserializer<RegistrationClientProvisioningRequest> {

    RegistrationClientProvisioningRequestStdDeserializer() {
        super(RegistrationClientProvisioningRequest.class);
    }

    @Override
    protected RegistrationClientProvisioningRequest deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var registeredClient = readJsonProperty(
                REGISTRATION_CLIENT_REQUEST_REGISTERED_CLIENT_JSON_FIELD,
                objectNode,
                objectCodec,
                SynthWaveRegisteredClient.class
        );
        var userSavingRequest = readJsonProperty(
                REGISTRATION_CLIENT_REQUEST_USER_SAVING_REQUEST_JSON_FIELD,
                objectNode,
                objectCodec,
                UserSavingRequest.class
        );
        return new RegistrationClientProvisioningRequestModel(
                registeredClient,
                userSavingRequest
        );
    }
}
