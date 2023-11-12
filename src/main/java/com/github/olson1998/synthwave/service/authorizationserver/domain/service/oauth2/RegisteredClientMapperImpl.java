package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveOAuth2RegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import com.github.olson1998.synthwave.support.rest.model.URLPath;
import com.github.olson1998.synthwave.support.rest.util.URIModel;
import com.github.olson1998.synthwave.support.rest.util.URIUtils;
import lombok.NonNull;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.olson1998.synthwave.support.rest.model.URLPathVariable.var;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.PROFILE;

public class RegisteredClientMapperImpl implements RegisteredClientMapper {

    @Override
    public RegisteredClient map(RegisteredClientConfig props){
        var code = props.getCompanyCode();
        var divi = props.getDivision();
        var clientId = props.getClientId();
        var redirectUris= decorateRedirectUris(props.getRedirectUris(), clientId);
        var postLogoutUris = decorateRedirectUris(props.getPostLogoutRedirectUris(), clientId);
        var tokenSettings = props.getTokenSettings();
        var clientSettings = buildClientSettings(props);
        var authorizationGrantTypesSet = props.getAuthorizationGrantTypes();
        var clientAuthenticationMethodsSet = props.getClientAuthenticationMethods();
        var registeredClientBuilder = RegisteredClient.withId(props.getClientId())
                .scopes(strings -> strings.addAll(List.of(OPENID, PROFILE)))
                .clientName(props.getUsername())
                .clientIdIssuedAt(props.getRegisteredClientId().getInstant())
                .clientSecret(props.getPasswordValue())
                .redirectUris(uris -> uris.addAll(redirectUris))
                .postLogoutRedirectUris(uris -> uris.addAll(postLogoutUris))
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(authorizationGrantTypesSet))
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(clientAuthenticationMethodsSet));
        Optional.ofNullable(props.getPasswordExpireTime()).ifPresent(registeredClientBuilder::clientSecretExpiresAt);
        return new SynthWaveOAuth2RegisteredClient(
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

    private Set<String> decorateRedirectUris(Set<String> redirectUris,@NonNull  String clientId){
        return redirectUris.stream()
                .map(URIUtils::traverse)
                .map(uriModel -> decorateURI(uriModel, clientId))
                .collect(Collectors.toSet());
    }

    private String decorateURI(URIModel uriModel,@NonNull  String clientId){
        var clientIdVar = var("{clientId}", clientId);
        var path = Optional.ofNullable(uriModel.getPath())
                .map(urlPath -> decoratePath(urlPath, clientId))
                .orElseGet(()-> URLPath.of("/{clientId}", clientIdVar));
        return URIModel.builder(uriModel.getHttpProtocol(), uriModel.getHost())
                .port(uriModel.getPort())
                .path(path)
                .urlParameters(uriModel.getUrlParams())
                .build()
                .toUri()
                .toString();
    }

    private URLPath decoratePath(@NonNull URLPath path,@NonNull String clientId){
        String decoratedPath;
        var clientIdVar = var("{clientId}", clientId);
        var rawPath = path.getRaw();
        if(!rawPath.contains("{clientId}")){
            decoratedPath = rawPath + "/{clientId}";
        }else {
            decoratedPath = rawPath;
        }
        return URLPath.of(decoratedPath, clientIdVar);
    }

}
