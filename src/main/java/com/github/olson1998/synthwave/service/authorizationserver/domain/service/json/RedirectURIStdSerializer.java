package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
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
        writeField("uri", redirectUri.getRedirectUri(), jsonGenerator);
    }
}
