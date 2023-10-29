package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.OAuth2AccessTokenPayload;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.util.Set;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2TokenMapperImpl.*;

class OAuth2AccessTokenPayloadStdDeserializer extends AbstractObjectStdDeserializer<OAuth2AccessTokenPayload> {


    protected OAuth2AccessTokenPayloadStdDeserializer() {
        super(OAuth2AccessTokenPayload.class);
    }

    @Override
    protected OAuth2AccessTokenPayload deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var type = readJsonProperty(
                ACCESS_TOKEN_TYPE_PROP,
                objectNode,
                objectCodec,
                OAuth2AccessToken.TokenType.class,
                true
        );
        var scopes = readJsonProperty(
                ACCESS_TOKEN_SCOPES_PROP,
                objectNode,
                objectCodec,
                new TypeReference<Set<String>>() {
                },
                true
        );
        return new OAuth2AccessTokenPayload(type, scopes);
    }
}
