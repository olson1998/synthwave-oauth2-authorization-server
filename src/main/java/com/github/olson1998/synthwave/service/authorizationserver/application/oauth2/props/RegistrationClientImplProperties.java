package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.properties.RegistrationClientProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.authorizationserver.registration-client")
public class RegistrationClientImplProperties implements RegistrationClientProperties {

    private String clientId = "registration-client";

    private String clientSecret = "registration";
}
