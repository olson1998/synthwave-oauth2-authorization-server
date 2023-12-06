package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.AuthorizationServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

@Configuration
@RequiredArgsConstructor
public class OAuth2AuthorizationServerSettingsConfig {

    private final AuthorizationServerProperties endpointsProperties;

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        var oauth2 = endpointsProperties.getOauth2();
        var oidcEndpoints = endpointsProperties.getOidc();
        var tokenEndpoints = endpointsProperties.getToken();
        var deviceEndpoints = endpointsProperties.getDevice();
        var oidcPath = oidcEndpoints.getPath();
        var tokenPath = tokenEndpoints.getPath();
        var devicePath = deviceEndpoints.getPath();
        var oauth2Path = oauth2.getPath();
        return AuthorizationServerSettings.builder()
                .jwkSetEndpoint(oauth2Path + oauth2.getJwkEndpoint())
                .authorizationEndpoint(oauth2Path + oauth2.getAuthorizeEndpoint())
                .tokenEndpoint(tokenPath + tokenEndpoints.getTokenEndpoint())
                .tokenRevocationEndpoint(tokenPath + tokenEndpoints.getRevocationEndpoint())
                .tokenIntrospectionEndpoint(tokenEndpoints.getIntrospectionEndpoint())
                .oidcUserInfoEndpoint(oidcPath + oidcEndpoints.getUserInfoEndpoint())
                .oidcLogoutEndpoint(oidcPath + oidcEndpoints.getLogoutEndpoint())
                .oidcClientRegistrationEndpoint(oidcPath + oidcEndpoints.getClientRegistrationEndpoint())
                .deviceAuthorizationEndpoint(devicePath + deviceEndpoints.getAuthorizationEndpoint())
                .deviceVerificationEndpoint(devicePath + deviceEndpoints.getVerificationEndpoint())
                .build();
    }
}
