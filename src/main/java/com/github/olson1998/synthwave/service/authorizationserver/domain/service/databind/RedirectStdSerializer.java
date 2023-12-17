package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel.REDIRECT_SCOPE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel.REDIRECT_URI_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel.Scope.POST_LOGIN;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.RedirectModel.Scope.POST_LOGOUT;

class RedirectStdSerializer extends AbstractObjectStdSerializer<Redirect> {

    protected RedirectStdSerializer() {
        super(Redirect.class);
    }

    @Override
    protected void serializeObject(Redirect redirect, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(REDIRECT_URI_JSON_FIELD, redirect.getUri(), jsonGenerator, true);
        if(redirect.isPostLogin()){
            writeField(REDIRECT_SCOPE_JSON_FIELD, POST_LOGIN, jsonGenerator);
        } else if (redirect.isPostLogout()) {
            writeField(REDIRECT_SCOPE_JSON_FIELD, POST_LOGOUT, jsonGenerator);
        }
    }
}
