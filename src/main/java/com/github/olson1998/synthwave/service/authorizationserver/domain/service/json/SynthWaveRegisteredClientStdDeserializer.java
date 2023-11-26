package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;

import java.io.IOException;

class SynthWaveRegisteredClientStdDeserializer extends AbstractObjectStdDeserializer<AbstractSynthWaveRegisteredClient> {

    private final RegisteredClientStdDeserializer registeredClientStdDeserializer;

    SynthWaveRegisteredClientStdDeserializer(RegisteredClientStdDeserializer registeredClientStdDeserializer) {
        super(AbstractSynthWaveRegisteredClient.class);
        this.registeredClientStdDeserializer = registeredClientStdDeserializer;
    }

    @Override
    protected SynthWaveRegisteredClient deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var code = readJsonProperty("company_code", objectNode, objectCodec, String.class, true);
        var divi = readJsonProperty("division", objectNode, objectCodec, String.class, true);
        var registeredClient = registeredClientStdDeserializer.deserializeObjectNode(objectNode, objectCodec, p, ctxt);
        return new SynthWaveRegisteredClient(code, divi, registeredClient);
    }
}
