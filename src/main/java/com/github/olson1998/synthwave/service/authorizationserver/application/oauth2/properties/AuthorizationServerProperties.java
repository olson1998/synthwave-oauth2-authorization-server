package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.service.authorizationserver")
public class AuthorizationServerProperties {

    private final OAuth2EndpointsProperties oauth2 = new OAuth2EndpointsProperties();

    private final OidcEndpointsProperties oidc = new OidcEndpointsProperties();

    private final TokenEndpointsProperties token = new TokenEndpointsProperties();

    private final DeviceEndpointsProperties device = new DeviceEndpointsProperties();

    @Data
    @Validated
    public static class OAuth2EndpointsProperties{

        private String path = "/oauth2-rest";

        private String jwkEndpoint = "/jwk";

        private String authorizeEndpoint = "/authorize";
    }

    @Data
    @Validated
    public static class OidcEndpointsProperties{

        private String path = "/oidc-rest";

        private String userInfoEndpoint = "/user-info";

        private String logoutEndpoint = "/logout";

        private String clientRegistrationEndpoint = "/registration";
    }

    @Data
    @Validated
    public static class TokenEndpointsProperties{

        private String path = "/token-rest";

        private String tokenEndpoint = "/token";

        private String revocationEndpoint = "/revoke";

        private String introspectionEndpoint = "/introspect";
    }

    @Data
    @Validated
    public static class DeviceEndpointsProperties{

        private String path = "/device-rest";

        private String authorizationEndpoint = "/authorize";

        private String verificationEndpoint = "/verify";
    }
}
