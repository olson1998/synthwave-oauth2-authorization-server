package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdSerializer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

class SignatureAlgorithmStdSerializer extends AbstractTextStdSerializer<SignatureAlgorithm> {

    SignatureAlgorithmStdSerializer() {
        super(SignatureAlgorithm.class);
    }

    @Override
    protected String serializeObject(SignatureAlgorithm object, SerializerProvider serializerProvider) {
        return object.getName();
    }
}
