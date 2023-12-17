package com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.io.IOException;
import java.util.Optional;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.fields.ClientSettingsJsonFields.*;

class ClientSettingsStdDeserializer extends AbstractObjectStdDeserializer<ClientSettings> {

    ClientSettingsStdDeserializer() {
        super(ClientSettings.class);
    }

    @Override
    protected ClientSettings deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var builder = ClientSettings.builder();
        Optional.ofNullable(readJsonProperty(CLIENT_SETTINGS_REQUIRE_PROOF_KEY, objectNode, objectCodec, Boolean.class))
                .ifPresent(builder::requireProofKey);
        Optional.ofNullable(readJsonProperty(CLIENT_SETTINGS_REQUIRE_AUTHORIZATION_CONSENT, objectNode, objectCodec, Boolean.class))
                .ifPresent(builder::requireAuthorizationConsent);
        Optional.ofNullable(readJsonProperty(CLIENT_SETTINGS_JWK_URL, objectNode, objectCodec, String.class))
                .ifPresent(builder::jwkSetUrl);
        Optional.ofNullable(readJsonProperty(CLIENT_SETTINGS_AUTHENTICATION_SIGNING_ALG, objectNode, objectCodec, JwsAlgorithm.class))
                .ifPresent(builder::tokenEndpointAuthenticationSigningAlgorithm);
        return builder.build();
    }

}
