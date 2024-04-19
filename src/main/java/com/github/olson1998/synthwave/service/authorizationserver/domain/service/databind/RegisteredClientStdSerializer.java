package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RegisteredClientModel;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.IOException;

class RegisteredClientStdSerializer extends StdSerializer<RegisteredClient> {

    RegisteredClientStdSerializer() {
        super(RegisteredClient.class);
    }

    @Override
    public void serialize(RegisteredClient registeredClient, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(registeredClient instanceof RegisteredClientModel registeredClientModel) {
            jsonGenerator.writeObject(registeredClientModel);
        } else {
            jsonGenerator.writeObject(new RegisteredClientModel(registeredClient));
        }
    }
}
