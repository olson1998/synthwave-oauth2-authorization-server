package com.github.olson1998.synthwave.service.authorizationserver.domain.service.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import com.github.olson1998.synthwave.support.web.model.PathVariables;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import com.github.olson1998.synthwave.support.jackson.AbstractObjectStdDeserializer;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient.*;
import static com.github.olson1998.synthwave.service.authorizationserver.domain.service.json.fields.RegisteredClientJsonFields.*;

class RegisteredClientStdDeserializer extends AbstractObjectStdDeserializer<RegisteredClient> {

    RegisteredClientStdDeserializer() {
        super(RegisteredClient.class);
    }

    @Override
    protected RegisteredClient deserializeObjectNode(ObjectNode objectNode, ObjectCodec objectCodec, JsonParser p, DeserializationContext ctxt) throws IOException {
        var id = readJsonProperty(REGISTERED_CLIENT_ID_JSON_PROPERTY, objectNode, objectCodec, TSID.class);
        var idString = Optional.ofNullable(id).map(TSID::toLong).map(String::valueOf).orElse("{?}");
        var companyCode = readJsonProperty(REGISTERED_CLIENT_COMPANY_CODE_JSON_PROPERTY, objectNode, objectCodec, String.class);
        var division = readJsonProperty(REGISTERED_CLIENT_DIVISION_JSON_PROPERTY, objectNode, objectCodec, String.class);
        var clientId = Optional.ofNullable(readJsonProperty(REGISTERED_CLIENT_CLIENT_ID_JSON_PROPERTY, objectNode, objectCodec, String.class))
                .orElse("{?}");
        var clientSecret = readJsonProperty(REGISTERED_CLIENT_SECRET_JSON_PROPERTY, objectNode, objectCodec, RegisteredClientSecret.class);
        var username = readJsonProperty(REGISTERED_CLIENT_NAME_JSON_PROPERTY, objectNode, objectCodec, String.class, true);
        var password = readJsonProperty(REGISTERED_CLIENT_USER_PASSWORD_JSON_PROPERTY, objectNode, objectCodec, Password.class);
        var tokenSettings =readJsonProperty(REGISTERED_CLIENT_TOKEN_SETTINGS_JSON_PROPERTY, objectNode, objectCodec, TokenSettings.class);
        var redirectUriModelSet = readJsonProperty(REDIRECT_URIS_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<URIModel>>() {
        });
        var redirectUriSet = customizeRedirectURISet(redirectUriModelSet, clientId, username);
        var postLogoutRedirectModelUris = readJsonProperty(POST_LOGOUT_REDIRECT_URIS_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<URIModel>>(){
        });
        var postLogoutRedirectUris = customizeRedirectURISet(postLogoutRedirectModelUris, clientId, username);
        var authorizationGrantTypes = readJsonProperty(AUTHORIZATION_GRANT_TYPES_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<AuthorizationGrantType>>() {
        });
        var clientAuthenticationMethods = readJsonProperty(CLIENT_AUTHENTICATION_METHODS_JSON_PROPERTY, objectNode, objectCodec, new TypeReference<Set<ClientAuthenticationMethod>>() {
        });
        var registeredClientBuilder = RegisteredClient.withId(idString)
                .clientId(clientId)
                .clientName(username)
                .clientIdIssuedAt(Optional.ofNullable(id).map(TSID::getInstant).orElse(null))
                .clientAuthenticationMethods(clientAuthenticationMethodsSet -> clientAuthenticationMethodsSet.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(authorizationGrantTypesSet -> authorizationGrantTypesSet.addAll(authorizationGrantTypes))
                .tokenSettings(tokenSettings)
                .postLogoutRedirectUris(postLogoutRedirectUriSet -> postLogoutRedirectUriSet.addAll(postLogoutRedirectUris))
                .redirectUris(redirectUris -> redirectUris.addAll(redirectUriSet));
        Optional.ofNullable(clientSecret).ifPresent(secret ->{
            registeredClientBuilder.clientSecret(secret.getValue());
            Optional.ofNullable(secret.getExpiresDateTime())
                    .map(MutableDateTimeConverter::new)
                    .map(MutableDateTimeConverter::toJavaInstant)
                    .ifPresent(registeredClientBuilder::clientSecretExpiresAt);
        });
        var registeredClient = registeredClientBuilder.build();
        if(division != null && companyCode != null){
            return new SynthWaveRegisteredClient(companyCode, division,password, registeredClient, clientSecret);
        }else {
            return registeredClient;
        }
    }

    private Set<String> customizeRedirectURISet(Set<URIModel> uriModelSet, String clientId, String username){
        if(uriModelSet == null){
            return null;
        }else {
            return uriModelSet.stream()
                    .map(uriModel -> customizeRedirectURI(uriModel, clientId, username))
                    .map(URIModel::toURI)
                    .map(URI::toString)
                    .collect(Collectors.toSet());
        }
    }

    private URIModel customizeRedirectURI(URIModel uriModel, String clientId, String username){
        uriModel.getPath().customize(pathVariables -> fillPathVariables(pathVariables, clientId, username));
        return uriModel;
    }

    private void fillPathVariables(PathVariables pathVariables, String clientId, String username){
        if(pathVariables.containsVariable("{clientId}")){
            pathVariables.setValue("{clientId}", clientId);
        }
        if(pathVariables.containsVariable("{username}")){
            pathVariables.setValue("{username}", username);
        }
    }
}
