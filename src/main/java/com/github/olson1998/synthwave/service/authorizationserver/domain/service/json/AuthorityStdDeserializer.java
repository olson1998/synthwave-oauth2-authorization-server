package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.AuthorityJson;

import java.io.IOException;

public class AuthorityStdDeserializer extends StdDeserializer<Authority> {

    AuthorityStdDeserializer() {
        super(AuthorityJson.class);
    }

    @Override
    public Authority deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var value = jsonParser.getValueAsString();
        return new AuthorityJson(value);
    }
}
