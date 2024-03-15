package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @Data
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwkProperties {

        public static final String OAUTH2_AUTHORIZATION_SERVER_JWK_PROPERTIES_VALUE = "#{" + OAUTH2_AUTHORIZATION_SERVER_PROPERTIES_BEAN + ".jwk}";

        @NotNull
        private String algorithm = "RSASSA-PSS";

        @Min(value = 1, message = "Minimal key size is 1024")
        private int keySize = 512;
    }
}
