package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdSerializer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.io.IOException;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.json.fields.ClientSettingsJsonFields.*;

class ClientSettingsStdSerializer extends AbstractObjectStdSerializer<ClientSettings> {

    ClientSettingsStdSerializer() {
        super(ClientSettings.class);
    }

    @Override
    protected void serializeObject(ClientSettings clientSettings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        writeField(CLIENT_SETTINGS_REQUIRE_PROOF_KEY, clientSettings.isRequireProofKey(), jsonGenerator);
        writeField(CLIENT_SETTINGS_REQUIRE_AUTHORIZATION_CONSENT, clientSettings.isRequireAuthorizationConsent(), jsonGenerator);
        writeField(CLIENT_SETTINGS_JWK_URL, clientSettings.getJwkSetUrl(), jsonGenerator);
        writeField(CLIENT_SETTINGS_AUTHENTICATION_SIGNING_ALG, clientSettings.getTokenEndpointAuthenticationSigningAlgorithm(), jsonGenerator);
    }
}
