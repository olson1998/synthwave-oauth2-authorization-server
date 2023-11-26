package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;

public interface RegistrationClientProvisioningRepository {

    @Transactional(rollbackFor = Exception.class)
    void provision();

    @Transactional(rollbackFor = Exception.class)
    @Async(ASYNC_TASK_EXEC)
    CompletableFuture<Void> provisionAsync();
}
