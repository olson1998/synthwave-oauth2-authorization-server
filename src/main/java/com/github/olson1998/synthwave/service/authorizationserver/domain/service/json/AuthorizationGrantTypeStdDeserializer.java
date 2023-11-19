package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.IOException;

class AuthorizationGrantTypeStdDeserializer extends StdDeserializer<AuthorizationGrantType> {

    AuthorizationGrantTypeStdDeserializer() {
        super(AuthorizationGrantType.class);
    }

    @Override
    public AuthorizationGrantType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var objectCodec = jsonParser.getCodec();
        var jsonTree = objectCodec.readTree(jsonParser);
        if(jsonTree instanceof TextNode textNode){
            return new AuthorizationGrantType(textNode.textValue());
        } else if (jsonTree instanceof NullNode) {
            return null;
        }else {
            throw new IOException("Expected text node but got: " + jsonTree.getClass());
        }
    }
}
