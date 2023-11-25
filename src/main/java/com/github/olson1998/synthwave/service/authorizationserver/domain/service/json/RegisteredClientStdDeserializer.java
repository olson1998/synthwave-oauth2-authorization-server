package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.sythwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.json.fields.RegisteredClientJsonFields.*;

public class RegisteredClientStdDeserializer extends AbstractObjectStdDeserializer<RegisteredClient> {

    protected RegisteredClientStdDeserializer() {
        super(RegisteredClient.class);
    }

    @Override
    protected RegisteredClient deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var id = readJsonProperty(REGISTERED_CLIENT_ID_JSON_PROPERTY, objectNode, objectCodec, TSID.class);
        var idString = Optional.ofNullable(id).map(TSID::toLong).map(String::valueOf).orElse("{?}");
        var clientId = Optional.ofNullable(readJsonProperty(REGISTERED_CLIENT_CLIENT_ID_JSON_PROPERTY, objectNode, objectCodec, String.class))
                .orElse("{?}");
        var username = readJsonProperty(REGISTERED_CLIENT_NAME_JSON_PROPERTY, objectNode, objectCodec, String.class, true);
        var tokenSettings =readJsonProperty(REGISTERED_CLIENT_TOKEN_SETTINGS_JSON_PROPERTY, objectNode, objectCodec, TokenSettings.class);
        var redirectUriSet = readJsonProperty(REDIRECT_URIS_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<String>>() {
        });
        var postLogoutRedirectUris = readJsonProperty(POST_LOGOUT_REDIRECT_URIS_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<String>>(){
        });
        var authorizationGrantTypes = readJsonProperty(AUTHORIZATION_GRANT_TYPES_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<AuthorizationGrantType>>() {
        });
        var clientAuthenticationMethods = readJsonProperty(CLIENT_AUTHENTICATION_METHODS_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<ClientAuthenticationMethod>>() {
        });
        return RegisteredClient.withId(idString)
                .clientId(clientId)
                .clientName(username)
                .clientIdIssuedAt(Optional.ofNullable(id).map(TSID::getInstant).orElse(null))
                .clientAuthenticationMethods(clientAuthenticationMethodsSet -> clientAuthenticationMethodsSet.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(authorizationGrantTypesSet -> authorizationGrantTypesSet.addAll(authorizationGrantTypes))
                .tokenSettings(tokenSettings)
                .postLogoutRedirectUris(postLogoutRedirectUriSet -> postLogoutRedirectUriSet.addAll(postLogoutRedirectUris))
                .redirectUris(redirectUris -> redirectUris.addAll(redirectUriSet))
                .build();
    }
}
