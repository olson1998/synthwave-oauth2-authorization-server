package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;

import java.io.IOException;

class JwsAlgorithmStdSerializer extends StdSerializer<JwsAlgorithm> {

    JwsAlgorithmStdSerializer() {
        super(JwsAlgorithm.class);
    }

    @Override
    public void serialize(JwsAlgorithm jwsAlgorithm, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(jwsAlgorithm == null){
            jsonGenerator.writeNull();
        }else {
            jsonGenerator.writeString(jwsAlgorithm.getName());
        }
    }
}
