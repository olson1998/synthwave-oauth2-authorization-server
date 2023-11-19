package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "synthwave.service.authorizationserver.oauth2.registration-client.source=file")
public class RegistrationClientConfig {


}
