package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

class ClientAuthenticationMethodsStdDeserializer extends AbstractTextStdDeserializer<ClientAuthenticationMethod> {

    ClientAuthenticationMethodsStdDeserializer() {
        super(ClientAuthenticationMethod.class);
    }

    @Override
    protected ClientAuthenticationMethod deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        return new ClientAuthenticationMethod(textValue);
    }

}
