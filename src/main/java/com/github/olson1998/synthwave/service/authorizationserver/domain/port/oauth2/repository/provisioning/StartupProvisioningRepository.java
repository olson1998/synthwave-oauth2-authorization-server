package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

public interface StartupProvisioningRepository {

    @Transactional(rollbackFor = Exception.class)
    void provision();

    @Transactional(rollbackFor = Exception.class)
    CompletableFuture<Void> provisionAsync();
}
