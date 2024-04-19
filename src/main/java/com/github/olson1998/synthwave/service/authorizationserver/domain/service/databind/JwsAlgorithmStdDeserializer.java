package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.util.Arrays;

class JwsAlgorithmStdDeserializer extends AbstractTextStdDeserializer<JwsAlgorithm> {

    JwsAlgorithmStdDeserializer() {
        super(JwsAlgorithm.class);
    }

    @Override
    protected JwsAlgorithm deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        if(Arrays.stream(MacAlgorithm.values()).anyMatch(macAlgorithm -> macAlgorithm.getName().equals(textValue))) {
            return MacAlgorithm.from(textValue);
        } else {
            return SignatureAlgorithm.from(textValue);
        }
    }
}
