package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdSerializer;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;

import java.io.IOException;

class JwsAlgorithmStdSerializer extends AbstractTextStdSerializer<JwsAlgorithm> {

    JwsAlgorithmStdSerializer() {
        super(JwsAlgorithm.class);
    }

    @Override
    protected String serializeObject(JwsAlgorithm object, SerializerProvider serializerProvider) {
        return object.getName();
    }
}
