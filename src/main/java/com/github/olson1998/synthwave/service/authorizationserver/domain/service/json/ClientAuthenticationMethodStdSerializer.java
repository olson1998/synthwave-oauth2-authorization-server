package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdSerializer;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

class ClientAuthenticationMethodStdSerializer extends AbstractTextStdSerializer<ClientAuthenticationMethod> {

    ClientAuthenticationMethodStdSerializer() {
        super(ClientAuthenticationMethod.class);
    }

    @Override
    protected String serializeObject(ClientAuthenticationMethod object, SerializerProvider serializerProvider) {
        return object.getValue();
    }
}
