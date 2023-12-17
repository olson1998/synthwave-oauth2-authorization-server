package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdSerializer;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;

class JwsAlgorithmStdSerializer extends AbstractTextStdSerializer<JwsAlgorithm> {

    JwsAlgorithmStdSerializer() {
        super(JwsAlgorithm.class);
    }

    @Override
    protected String serializeObject(JwsAlgorithm object, SerializerProvider serializerProvider) {
        return object.getName();
    }
}
