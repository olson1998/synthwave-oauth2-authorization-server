package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdSerializer;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson.REDIRECT_URI_TYPE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson.REDIRECT_URI_VALUE_JSON_FIELD;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson.Type.POST_LOGIN;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.json.RedirectUriJson.Type.POST_LOGOUT;

class RedirectUriStdSerializer extends AbstractObjectStdSerializer<RedirectUri> {

    protected RedirectUriStdSerializer() {
        super(RedirectUri.class);
    }

    @Override
    protected void serializeObject(RedirectUri redirectUri, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(redirectUri.isPostLogin()){
            writeField(REDIRECT_URI_TYPE_JSON_FIELD, POST_LOGIN, jsonGenerator);
        } else if (redirectUri.isPostLogout()) {
            writeField(REDIRECT_URI_TYPE_JSON_FIELD, POST_LOGOUT, jsonGenerator);
        }
        writeField(REDIRECT_URI_VALUE_JSON_FIELD, redirectUri.getRedirectUri(), jsonGenerator);
    }
}
