package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.StartupProvisioningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.provisioning.type", havingValue = "sync", matchIfMissing = true)
public class StartupProvisioningExecutor {

    private final StartupProvisioningRepository startupProvisioningRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void provisionRegistrationClient(){
        startupProvisioningRepository.provision();
    }
}
