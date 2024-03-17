package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

class SignatureAlgorithmStdDeserializer extends AbstractTextStdDeserializer<SignatureAlgorithm> {

    SignatureAlgorithmStdDeserializer() {
        super(SignatureAlgorithm.class);
    }

    @Override
    protected SignatureAlgorithm deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        return SignatureAlgorithm.from(textValue);
    }
}
