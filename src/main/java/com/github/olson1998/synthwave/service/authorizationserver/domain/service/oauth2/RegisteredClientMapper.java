package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientConfig;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.PROFILE;

class RegisteredClientMapper {

    RegisteredClient map(RegisteredClientConfig props){
        var code = props.getCompanyCode();
        var divi = props.getDivision();
        var id = props.getId();
        var idString = String.valueOf(id.toLong());
        var clientId = props.getClientId();
        var tokenSettings = props.getTokenSettings();
        var clientSettings = buildClientSettings(props);
        var authorizationGrantTypesSet = props.getAuthorizationGrantTypes();
        var clientAuthenticationMethodsSet = props.getClientAuthenticationMethods();
        var registeredClientBuilder = RegisteredClient.withId(idString)
                .scopes(strings -> strings.addAll(List.of(OPENID, PROFILE)))
                .clientId(clientId)
                .clientName(props.getUsername())
                .clientIdIssuedAt(id.getInstant())
                .clientSecret(props.getPasswordValue())
                .redirectUris(uris -> uris.addAll(props.getRedirectUris()))
                .postLogoutRedirectUris(uris -> uris.addAll(props.getPostLogoutRedirectUris()))
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
}
