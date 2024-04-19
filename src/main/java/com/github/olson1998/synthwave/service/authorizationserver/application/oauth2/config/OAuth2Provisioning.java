package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationServerStartupProvisioning;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class OAuth2Provisioning {

    private final AuthorizationServerStartupProvisioning authorizationServerStartupProvisioning;

    @EventListener(ApplicationStartedEvent.class)
    public void afterPropertiesSet() {
        authorizationServerStartupProvisioning.provisionOnStartup();
    }
}
