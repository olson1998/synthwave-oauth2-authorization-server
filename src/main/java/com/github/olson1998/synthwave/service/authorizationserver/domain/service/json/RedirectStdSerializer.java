package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLoginRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.PostLogoutRedirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

class RedirectStdSerializer extends AbstractObjectStdSerializer<Redirect> {

    protected RedirectStdSerializer() {
        super(Redirect.class);
    }

    @Override
    protected void serializeObject(Redirect redirect, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(redirect.isPostLogin()){
            writeField("type", PostLoginRedirect.TYPE, jsonGenerator);
        } else if (redirect.isPostLogout()) {
            writeField("type", PostLogoutRedirect.TYPE, jsonGenerator);
        }
        writeField("uri", redirect.getUri(), jsonGenerator);
    }
}
