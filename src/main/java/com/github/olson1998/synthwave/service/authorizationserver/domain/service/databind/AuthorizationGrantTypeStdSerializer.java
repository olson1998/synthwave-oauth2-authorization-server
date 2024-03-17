package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdSerializer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

class AuthorizationGrantTypeStdSerializer extends AbstractTextStdSerializer<AuthorizationGrantType> {

    AuthorizationGrantTypeStdSerializer() {
        super(AuthorizationGrantType.class);
    }

    @Override
    protected String serializeObject(AuthorizationGrantType object, SerializerProvider serializerProvider) {
        return object.getValue();
    }
}
