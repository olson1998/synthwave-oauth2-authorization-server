package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RedirectProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.StartupProvisioningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class StartupProvisioningService implements StartupProvisioningRepository {

    private final RedirectProvisioningSupplier redirectProvisioningSupplier;

    private final RegistrationClientProvisioningSupplier registrationClientProvisioningSupplier;

    private final RedirectRepository redirectRepository;

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void provision() {

    }

    @Override
    public CompletableFuture<Void> provisionAsync() {
        return null;
    }
}
