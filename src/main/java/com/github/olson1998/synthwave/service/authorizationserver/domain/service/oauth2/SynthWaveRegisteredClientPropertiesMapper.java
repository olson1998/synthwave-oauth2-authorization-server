package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.SynthWaveRegisteredClientInstance;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import com.github.olson1998.synthwave.support.rest.model.URLPath;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;
import java.util.Optional;

import static com.github.olson1998.synthwave.support.rest.model.URLPathVariable.var;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.PROFILE;

public class SynthWaveRegisteredClientPropertiesMapper {

    public RegisteredClient map(SynthWaveRegisteredClientProperties props){
        var code = props.getCompanyCode();
        var divi = props.getDivision();
        var clientId = props.getClientId();
        var registeredClientSettings = props.getRegisteredClientSettings();
        var clientSettings = finishBuildingClientSettings(registeredClientSettings.fabricateClientSettingsBuilder());
        var tokenSettings = props.getTokenSettings();
        var registeredClientBuilder = RegisteredClient.withId(props.getClientId())
                .scopes(strings -> strings.addAll(List.of(OPENID, PROFILE)))
                .clientName(props.getUsername())
                .clientIdIssuedAt(props.getRegisteredClientId().getInstant())
                .clientSecret(props.getPasswordValue())
                .redirectUris(strings -> strings.addAll(props.getRedirectUris()))
                .postLogoutRedirectUris(strings -> strings.addAll(props.getPostLogoutRedirectUris()))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(registeredClientSettings.getClientAuthenticationMethods()))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(registeredClientSettings.getAuthorizationGrantTypes()));
        Optional.ofNullable(props.getPasswordExpireTime()).ifPresent(registeredClientBuilder::clientSecretExpiresAt);
        return new SynthWaveRegisteredClientInstance(
                code,
                divi,
                registeredClientBuilder.build()
        );
    }

    private ClientSettings finishBuildingClientSettings(ClientSettings.Builder builder){
        return builder
                .jwkSetUrl("http://localhost:8095")
                .tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }

    private String writeRedirectUri(String baseUri, String clientId){
        return baseUri + URLPath.of(
                "/{client}",
                var("{client}", clientId)
        );
    }

}
