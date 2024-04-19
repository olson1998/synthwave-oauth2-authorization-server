package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientModel;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.IOException;

class RegisteredClientStdDeserializer extends StdDeserializer<RegisteredClient> {

    RegisteredClientStdDeserializer() {
        super(RegisteredClient.class);
    }

    @Override
    public RegisteredClient deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return jsonParser.getCodec().readValue(jsonParser, RegisteredClientModel.class);
    }

}
