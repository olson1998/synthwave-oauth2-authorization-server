package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.olson1998.sythwave.support.jackson.exception.IORuntimeException;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import java.io.IOException;
import java.util.HashSet;

class JwsAlgorithmStdDeserializer extends StdDeserializer<JwsAlgorithm> {

    JwsAlgorithmStdDeserializer() {
        super(JwsAlgorithm.class);
    }

    @Override
    public JwsAlgorithm deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var objectCodec = jsonParser.getCodec();
        var jsonTree = objectCodec.readTree(jsonParser);
        if(jsonTree instanceof NullNode){
            return null;
        } else if (jsonTree instanceof TextNode textNode) {
            return getAlgorithm(textNode.asText());
        }else {
            throw new IOException("Expected instance of TextNode but got: " + jsonTree.getClass().getCanonicalName());
        }
    }

    private JwsAlgorithm getAlgorithm(String algName){
        if(algName.equals(JwsAlgorithms.ES256)){
            return SignatureAlgorithm.ES256;
        } else if (algName.equals(JwsAlgorithms.ES384)) {
            return SignatureAlgorithm.ES384;
        } else if (algName.equals(JwsAlgorithms.ES512)) {
            return SignatureAlgorithm.ES512;
        } else if (algName.equals(JwsAlgorithms.HS256)) {
            return MacAlgorithm.HS256;
        } else if (algName.equals(JwsAlgorithms.HS384)) {
            return MacAlgorithm.HS384;
        } else if (algName.equals(JwsAlgorithms.HS512)) {
            return MacAlgorithm.HS512;
        } else if (algName.equals(JwsAlgorithms.PS256)) {
            return SignatureAlgorithm.PS256;
        } else if (algName.equals(JwsAlgorithms.PS384)) {
            return SignatureAlgorithm.PS384;
        } else if (algName.equals(JwsAlgorithms.PS512)) {
            return SignatureAlgorithm.PS512;
        } else if (algName.equals(JwsAlgorithms.RS256)) {
            return SignatureAlgorithm.RS256;
        } else if (algName.equals(JwsAlgorithms.RS384)) {
            return SignatureAlgorithm.RS384;
        } else if (algName.equals(JwsAlgorithms.RS512)) {
            return SignatureAlgorithm.RS512;
        }else {
            throw new IORuntimeException(new IOException("Unknown JWS algorithm: " + algName));
        }
    }
}
