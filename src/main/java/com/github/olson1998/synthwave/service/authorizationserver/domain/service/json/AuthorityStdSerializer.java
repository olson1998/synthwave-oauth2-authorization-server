package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class AuthorityStdSerializer extends StdSerializer<Authority> {

    AuthorityStdSerializer() {
        super(Authority.class);
    }

    @Override
    public void serialize(Authority authority, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(authority.getValue());
    }
}
