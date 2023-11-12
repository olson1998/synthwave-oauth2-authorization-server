package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

class RedirectURIStdSerializer extends AbstractObjectStdSerializer<RedirectURI> {

    protected RedirectURIStdSerializer() {
        super(RedirectURI.class);
    }

    @Override
    protected void serializeObject(RedirectURI redirectUri, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(redirectUri.isPostLogin()){
            writeField("type", PostLoginRedirectURI.TYPE, jsonGenerator);
        } else if (redirectUri.isPostLogout()) {
            writeField("type", PostLogoutRedirectURI.TYPE, jsonGenerator);
        }
        writeField("uri", redirectUri.getUri(), jsonGenerator);
    }
}
