package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientSecretModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.ClientSecret;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;
import com.github.olson1998.synthwave.support.joda.converter.JavaInstantConverter;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields.RegisteredClientJsonFields.*;

class RegisteredClientStdSerializer extends AbstractObjectStdSerializer<RegisteredClient> {

    RegisteredClientStdSerializer() {
        super(RegisteredClient.class);
    }

    @Override
    protected void serializeObject(RegisteredClient registeredClient, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        var tsid = readTsid(registeredClient.getId());
        writeField(REGISTERED_CLIENT_ID_JSON_PROPERTY, tsid, jsonGenerator);
        writeField(REGISTERED_CLIENT_CLIENT_ID_JSON_PROPERTY, registeredClient.getClientId(), jsonGenerator);
        writeField(REGISTERED_CLIENT_NAME_JSON_PROPERTY, registeredClient.getClientName(), jsonGenerator);
        writeField(REGISTERED_CLIENT_ID_ISS_TMP_JSON_PROPERTY, registeredClient.getClientIdIssuedAt(), jsonGenerator);
        serializeClientSecret(registeredClient, jsonGenerator);
        writeField(REGISTERED_CLIENT_CLIENT_SETTINGS_JSON_PROPERTY, registeredClient.getClientSettings(), jsonGenerator, false);
        writeField(REGISTERED_CLIENT_TOKEN_SETTINGS_JSON_PROPERTY, registeredClient.getTokenSettings(), jsonGenerator, false);
        writeField(REDIRECT_URIS_JSON_PROPERTY, registeredClient.getRedirectUris(), jsonGenerator, false);
        writeField(POST_LOGOUT_REDIRECT_URIS_JSON_PROPERTY, registeredClient.getPostLogoutRedirectUris(), jsonGenerator, false);
        writeField(AUTHORIZATION_GRANT_TYPES_JSON_PROPERTY, registeredClient.getAuthorizationGrantTypes(), jsonGenerator, false);
        writeField(CLIENT_AUTHENTICATION_METHODS_JSON_PROPERTY, registeredClient.getClientAuthenticationMethods(), jsonGenerator, false);
    }

    private void serializeClientSecret(RegisteredClient registeredClient, JsonGenerator jsonGenerator){
        ClientSecret clientSecret;
        if(registeredClient instanceof AbstractSynthWaveRegisteredClient synthWaveRegisteredClient){
            clientSecret = synthWaveRegisteredClient.getClientSecretObject();
        }else {
            clientSecret = new ClientSecretModel(
                    registeredClient.getClientSecret(),
                    new JavaInstantConverter(registeredClient.getClientSecretExpiresAt()).toMutableDateTime()
            );
        }
        writeField(REGISTERED_CLIENT_SECRET_JSON_PROPERTY, clientSecret, jsonGenerator, false);
    }

    private TSID readTsid(String stringValue){
        var longValue = Long.parseLong(stringValue);
        return TSID.from(longValue);
    }
}
