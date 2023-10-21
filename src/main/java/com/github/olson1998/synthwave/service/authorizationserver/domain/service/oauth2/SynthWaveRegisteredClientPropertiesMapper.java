package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;

public class SynthWaveRegisteredClientPropertiesMapper {

    public RegisteredClient map(SynthWaveRegisteredClientProperties props){
        return RegisteredClient.withId(props.getClientId())
                .scope(OPENID)
                .clientName(props.getUsername())
                .clientIdIssuedAt(props.getRegisteredClientId().getInstant())
                .clientSecret(props.getPasswordValue())
                .redirectUris(strings -> strings.addAll(props.getRedirectUris()))
                .postLogoutRedirectUris(strings -> strings.addAll(props.getPostLogoutRedirectUris()))
                .build();
    }

}
