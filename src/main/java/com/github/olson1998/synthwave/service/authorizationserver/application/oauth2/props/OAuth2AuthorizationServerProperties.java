package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import static com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties.OAUTH2_AUTHORIZATION_SERVER_PROPERTIES_BEAN;

@Data
@Validated
@Configuration(OAUTH2_AUTHORIZATION_SERVER_PROPERTIES_BEAN)
@ConfigurationProperties(prefix = "synthwave.service.authorizationserver.oauth2")
public class OAuth2AuthorizationServerProperties {

    public static final String OAUTH2_AUTHORIZATION_SERVER_PROPERTIES_BEAN = "oauth2AuthorizationServerProperties";

    private final JwkProperties jwk = new JwkProperties();
    private final EndpointsProperties endpoint = new EndpointsProperties();

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwkProperties {

        public static final String OAUTH2_AUTHORIZATION_SERVER_JWK_PROPERTIES_VALUE = "#{" + OAUTH2_AUTHORIZATION_SERVER_PROPERTIES_BEAN + ".jwk}";

        @NotNull
        private String algorithm = "RSASSA-PSS";

        @Min(value = 1)
        private int keySize = 512;
    }

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EndpointsProperties {

        public static final String OAUTH2_AUTHORITZATION_SERVER_ENDPOINTS_PROPERTIES_VALUE = "#{" + OAUTH2_AUTHORIZATION_SERVER_PROPERTIES_BEAN + ".endpoint}";

        private String jwk = "/jwk";

        private String authorization = "/authorization";

        private final OidcEndpointProperties oidc = new OidcEndpointProperties();

        private final TokenEndpointProperties token = new TokenEndpointProperties();

        private final DeviceEndpointProperties device = new DeviceEndpointProperties();

        @Data
        @Validated
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class TokenEndpointProperties {

            private String endpoint ="/token";

            private String revocation ="/token/revoke";

            private String introspection = "/token/introspect";
        }

        @Data
        @Validated
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class DeviceEndpointProperties{

            private String authorization = "/device/authorize";

            private String verification = "/device/verify";
        }

        @Data
        @Validated
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class OidcEndpointProperties {

            private String logout = "/oidc/logout";

            private String clientRegistration = "/oidc/register";

            private String userInfo = "/oidc/user-info";
        }
    }
}
