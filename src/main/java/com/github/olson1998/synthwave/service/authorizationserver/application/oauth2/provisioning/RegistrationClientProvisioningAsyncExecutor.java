package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "synthwave.service.authorizationserver.oauth2.registration-client.provisioning.type", havingValue = "async")
public class RegistrationClientProvisioningAsyncExecutor {

    private final RegistrationClientProvisioningRepository registrationClientProvisioningRepository;

    @Async(ASYNC_TASK_EXEC)
    @EventListener(ApplicationStartedEvent.class)
    public CompletableFuture<Void> provisionRegistrationClientAsync(){
        return registrationClientProvisioningRepository.provisionAsync();
    }
}
