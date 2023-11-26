package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.support.rest.exception.PathVariableWritingException;
import com.github.olson1998.synthwave.support.rest.exception.URITraversingException;
import com.github.olson1998.synthwave.support.rest.exception.URIWritingException;
import com.github.olson1998.synthwave.support.rest.model.PathVariables;
import com.github.olson1998.synthwave.support.rest.util.URIModel;
import com.github.olson1998.synthwave.support.rest.util.URIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.PROFILE;

@Slf4j
class RegisteredClientMapper {

    RegisteredClient map(RegisteredClientConfig props){
        var code = props.getCompanyCode();
        var divi = props.getDivision();
        var id = props.getId();
        var idString = String.valueOf(id.toLong());
        var clientId = props.getClientId();
        var tokenSettings = props.getTokenSettings();
        var clientSettings = buildClientSettings(props);
        var redirectURI = writeRedirectURISet(props.getRedirectUris(), props);
        var postLogoutURI = writeRedirectURISet(props.getPostLogoutRedirectUris(), props);
        var authorizationGrantTypesSet = props.getAuthorizationGrantTypes();
        var clientAuthenticationMethodsSet = props.getClientAuthenticationMethods();
        var registeredClientBuilder = RegisteredClient.withId(idString)
                .scopes(strings -> strings.addAll(List.of(OPENID, PROFILE)))
                .clientId(clientId)
                .clientName(props.getUsername())
                .clientIdIssuedAt(id.getInstant())
                .clientSecret(props.getPasswordValue())
                .redirectUris(uris -> uris.addAll(redirectURI))
                .postLogoutRedirectUris(uris -> uris.addAll(postLogoutURI))
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(authorizationGrantTypesSet))
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(clientAuthenticationMethodsSet));
        Optional.ofNullable(props.getPasswordExpireTime()).ifPresent(registeredClientBuilder::clientSecretExpiresAt);
        return new SynthWaveRegisteredClient(
                code,
                divi,
                registeredClientBuilder.build()
        );
    }

    private ClientSettings buildClientSettings(RegisteredClientConfig registeredClientConfig){
        return ClientSettings.builder()
                .requireAuthorizationConsent(registeredClientConfig.isRequireAuthorizationConsent())
                .requireProofKey(registeredClientConfig.isRequireProofKey())
                .build();
    }

    private Set<String> writeRedirectURISet(Set<String> redirectUriSet, RegisteredClientConfig registeredClientConfig){
        return redirectUriSet.stream()
                .map(this::buildModelSafely)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(uriModel -> customizeURIModel(uriModel, registeredClientConfig))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::writeRedirectURI)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(URI::toString)
                .collect(Collectors.toSet());
    }

    private Optional<URIModel> buildModelSafely(String uri){
        try{
            return Optional.of(URIUtils.read(uri));
        }catch (URITraversingException e){
            log.error("Failed to map redirect URI: '{}', reason:", uri, e);
            return Optional.empty();
        }
    }

    private Optional<URIModel> customizeURIModel(URIModel uriModel, RegisteredClientConfig clientConfig){
        try{
            uriModel.getPath().customize(pathVariables -> fillPathVariables(pathVariables, clientConfig));
            return Optional.of(uriModel);
        }catch (PathVariableWritingException e){
            log.error("Failed to fill path variables to: {}, reason:",uriModel, e);
            return Optional.empty();
        }
    }

    private void fillPathVariables(PathVariables pathVariables, RegisteredClientConfig clientConfig){
        if(pathVariables.containsVariable("{clientId}")){
            pathVariables.setValue("{clientId}", clientConfig.getClientId());
        }
        if(pathVariables.containsVariable("{code}")){
            pathVariables.setValue("{code}", clientConfig.getCompanyCode());
        }
        if(pathVariables.containsVariable("{divi}")){
            pathVariables.setValue("{divi}", clientConfig.getDivision());
        }
    }

    private Optional<URI> writeRedirectURI(URIModel uriModel){
        try{
            return Optional.of(uriModel.toURI());
        }catch (URIWritingException e){
            log.error("Failed to write redirect URI: {}, reason:", uriModel, e);
            return Optional.empty();
        }
    }
}
