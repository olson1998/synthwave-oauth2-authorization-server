package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.support.joda.converter.MutableDateTimeConverter;
import com.github.olson1998.synthwave.support.web.exception.PathVariableWritingException;
import com.github.olson1998.synthwave.support.web.exception.URIReadingException;
import com.github.olson1998.synthwave.support.web.exception.URIWritingException;
import com.github.olson1998.synthwave.support.web.model.PathVariables;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import com.github.olson1998.synthwave.support.web.util.URIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.PROFILE;

@Slf4j
class RegisteredClientMapper {

    private final TokenSettingsMapper tokenSettingsMapper = new TokenSettingsMapper();

    RegisteredClient map(RegisteredClientConfig props){
        var code = props.getCompanyCode();
        var divi = props.getDivision();
        var id = props.getId();
        var idString = String.valueOf(id.toLong());
        var clientId = props.getClientId();
        var tokenSettings = tokenSettingsMapper.map(props);
        var clientSettings = buildClientSettings(props);
        var redirectURI = writeRedirectURISet(props.getRedirectUris(), props);
        var postLogoutURI = writeRedirectURISet(props.getPostLogoutRedirectUris(), props);
        var authorizationGrantTypesSet = props.getAuthorizationGrantTypes();
        var clientAuthenticationMethodsSet = props.getClientAuthenticationMethods();
        var secret = props.getRegisteredClientSecret();
        var registeredClientBuilder = RegisteredClient.withId(idString)
                .scopes(strings -> strings.addAll(List.of(OPENID, PROFILE)))
                .clientId(clientId)
                .clientName(props.getUsername())
                .clientIdIssuedAt(id.getInstant())
                .redirectUris(uris -> uris.addAll(transformURIModels(redirectURI, props)))
                .postLogoutRedirectUris(uris -> transformURIModels(postLogoutURI, props))
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(authorizationGrantTypesSet))
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(clientAuthenticationMethodsSet));
        Optional.ofNullable(secret).ifPresent(clientSecret ->{
            registeredClientBuilder.clientSecret(clientSecret.getValue());
            Optional.ofNullable(clientSecret.getExpiresDateTime()).ifPresent(expireAt ->{
                var expireAtInstant = new MutableDateTimeConverter(expireAt)
                        .toJavaInstant();
                Optional.ofNullable(expireAtInstant).ifPresent(registeredClientBuilder::clientSecretExpiresAt);
            });
        });
        return new SynthWaveRegisteredClient(
                code,
                divi,
                null,
                registeredClientBuilder.build(),
                secret,
                redirectURI,
                postLogoutURI
        );
    }

    private ClientSettings buildClientSettings(RegisteredClientConfig registeredClientConfig){
        var builder = ClientSettings.builder();
        registeredClientConfig.findRequireProofKey().ifPresent(builder::requireProofKey);
        registeredClientConfig.findRequireAuthorizationConsent().ifPresent(builder::requireAuthorizationConsent);
        return builder.build();
    }

    private Set<String> transformURIModels(Set<URIModel> uriModelSet, RegisteredClientConfig registeredClientConfig){
        return uriModelSet.stream()
                .map(uriModel -> customizeURIModel(uriModel, registeredClientConfig))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::writeRedirectURI)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(URI::toString)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<URIModel> writeRedirectURISet(Set<String> redirectUriSet, RegisteredClientConfig registeredClientConfig){
        return redirectUriSet.stream()
                .map(this::buildModelSafely)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(uriModel -> customizeURIModel(uriModel, registeredClientConfig))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Optional<URIModel> buildModelSafely(String uri){
        try{
            return Optional.of(URIUtils.read(uri));
        }catch (URIReadingException e){
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
