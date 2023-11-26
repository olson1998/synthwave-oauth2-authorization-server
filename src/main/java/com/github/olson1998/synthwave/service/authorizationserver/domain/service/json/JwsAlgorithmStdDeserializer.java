package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdDeserializer;
import com.github.olson1998.synthwave.support.jackson.exception.IORuntimeException;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.io.IOException;

class JwsAlgorithmStdDeserializer extends AbstractTextStdDeserializer<JwsAlgorithm> {

    JwsAlgorithmStdDeserializer() {
        super(JwsAlgorithm.class);
    }

    @Override
    protected JwsAlgorithm deserializeText(String textValue, ObjectCodec objectCodec, JsonParser jsonParser, DeserializationContext deserializationContext) {
        return getAlgorithm(textValue);
    }

    private JwsAlgorithm getAlgorithm(String algName){
        return switch (algName) {
            case JwsAlgorithms.ES256 -> SignatureAlgorithm.ES256;
            case JwsAlgorithms.ES384 -> SignatureAlgorithm.ES384;
            case JwsAlgorithms.ES512 -> SignatureAlgorithm.ES512;
            case JwsAlgorithms.HS256 -> MacAlgorithm.HS256;
            case JwsAlgorithms.HS384 -> MacAlgorithm.HS384;
            case JwsAlgorithms.HS512 -> MacAlgorithm.HS512;
            case JwsAlgorithms.PS256 -> SignatureAlgorithm.PS256;
            case JwsAlgorithms.PS384 -> SignatureAlgorithm.PS384;
            case JwsAlgorithms.PS512 -> SignatureAlgorithm.PS512;
            case JwsAlgorithms.RS256 -> SignatureAlgorithm.RS256;
            case JwsAlgorithms.RS384 -> SignatureAlgorithm.RS384;
            case JwsAlgorithms.RS512 -> SignatureAlgorithm.RS512;
            default -> throw new IORuntimeException(new IOException("Unknown JWS algorithm: " + algName));
        };
    }

}
