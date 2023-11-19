package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.io.IOException;

class ClientAuthenticationMethodsStdDeserializer extends StdDeserializer<ClientAuthenticationMethod> {

    ClientAuthenticationMethodsStdDeserializer() {
        super(ClientAuthenticationMethod.class);
    }

    @Override
    public ClientAuthenticationMethod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var objectCodec = jsonParser.getCodec();
        var treeNode = objectCodec.readTree(jsonParser);
        if(treeNode instanceof TextNode stringNode){
            return new ClientAuthenticationMethod(stringNode.textValue());
        } else if (treeNode instanceof NullNode) {
            return null;
        }else {
            throw new IOException("Expected text node but got: " + treeNode.getClass());
        }
    }
}
