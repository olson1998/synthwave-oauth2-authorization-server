package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.olson1998.sythwave.support.jackson.exception.IORuntimeException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.util.Optional;

class TokenTypeStdSerializer extends StdSerializer<OAuth2AccessToken.TokenType> {

    TokenTypeStdSerializer() {
        super(OAuth2AccessToken.TokenType.class);
    }

    @Override
    public void serialize(OAuth2AccessToken.TokenType tokenType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Optional.ofNullable(tokenType).ifPresentOrElse(value ->{
            try{
                jsonGenerator.writeString(value.getValue());
            }catch (IOException e){
                throw new IORuntimeException(e);
            }
        }, ()->{
            try{
                jsonGenerator.writeNull();
            }catch (IOException e){
                throw new IORuntimeException(e);
            }
        });
    }
}
