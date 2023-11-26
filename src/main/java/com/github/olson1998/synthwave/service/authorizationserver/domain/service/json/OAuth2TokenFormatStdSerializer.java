package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractTextStdSerializer;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

class OAuth2TokenFormatStdSerializer extends AbstractTextStdSerializer<OAuth2TokenFormat> {

    OAuth2TokenFormatStdSerializer() {
        super(OAuth2TokenFormat.class);
    }

    @Override
    protected String serializeObject(OAuth2TokenFormat object, SerializerProvider serializerProvider) {
        return object.getValue();
    }
}
