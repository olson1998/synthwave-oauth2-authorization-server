package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

class ClientAuthenticationMethodStdDeserializer extends AbstractTextStdDeserializer<ClientAuthenticationMethod> {

    ClientAuthenticationMethodStdDeserializer() {
        super(ClientAuthenticationMethod.class);
    }

    @Override
    protected ClientAuthenticationMethod deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        return new ClientAuthenticationMethod(textValue);
    }
}
