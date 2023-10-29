package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.util.Optional;

class TokenTypeStdDeserializer extends StdDeserializer<OAuth2AccessToken.TokenType> {


    TokenTypeStdDeserializer() {
        super(OAuth2AccessToken.TokenType.class);
    }

    @Override
    public OAuth2AccessToken.TokenType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return Optional.ofNullable(jsonParser.getValueAsString())
                .map(this::map)
                .orElse(null);
    }

    private OAuth2AccessToken.TokenType map(String type){
        if(type.equals(OAuth2AccessToken.TokenType.BEARER.getValue())){
            return OAuth2AccessToken.TokenType.BEARER;
        }else {
            throw new IllegalArgumentException("");
        }
    }
}
