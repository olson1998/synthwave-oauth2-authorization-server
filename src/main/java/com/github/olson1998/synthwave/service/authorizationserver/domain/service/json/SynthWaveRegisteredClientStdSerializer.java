package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient.REGISTERED_CLIENT_COMPANY_CODE_JSON_PROPERTY;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient.REGISTERED_CLIENT_DIVISION_JSON_PROPERTY;

class SynthWaveRegisteredClientStdSerializer extends AbstractObjectStdSerializer<AbstractSynthWaveRegisteredClient> {

    private final RegisteredClientStdSerializer registeredClientStdSerializer;

    SynthWaveRegisteredClientStdSerializer(RegisteredClientStdSerializer registeredClientStdSerializer) {
        super(AbstractSynthWaveRegisteredClient.class);
        this.registeredClientStdSerializer = registeredClientStdSerializer;
    }

    @Override
    protected void serializeObject(AbstractSynthWaveRegisteredClient synthWaveRegisteredClient, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(REGISTERED_CLIENT_COMPANY_CODE_JSON_PROPERTY, synthWaveRegisteredClient.getCompanyCode(), jsonGenerator);
        writeField(REGISTERED_CLIENT_DIVISION_JSON_PROPERTY, synthWaveRegisteredClient.getDivision(), jsonGenerator);
        registeredClientStdSerializer.serializeObject(synthWaveRegisteredClient, jsonGenerator, serializerProvider);
    }
}
