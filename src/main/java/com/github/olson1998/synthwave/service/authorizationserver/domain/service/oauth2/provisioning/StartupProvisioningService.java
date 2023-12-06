package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RedirectProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.StartupProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectRepository;
import com.github.olson1998.synthwave.support.pipeline.JobResult;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.PipelineInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class StartupProvisioningService implements StartupProvisioningRepository {

    private final RedirectProvisioningSupplier redirectProvisioningSupplier;

    private final RegistrationClientProvisioningSupplier registrationClientProvisioningSupplier;

    private final RedirectRepository redirectRepository;

    private final RegisteredClientProvisioningRepository registeredClientProvisioningRepository;

    @Override
    public void provision() {
        var redirectsSet = redirectProvisioningSupplier.get();
        var registeredClientSet = registrationClientProvisioningSupplier.get();
        registeredClientSet.forEach(registeredClientProvisioningRepository::provision);
    }

    @Override
    public CompletableFuture<Void> provisionAsync() {

    }

    private Void provisionRegisteredClient(RegisteredClient registeredClient){
        registeredClientProvisioningRepository.provision(registeredClient);
        return null;
    }

    private PipelineInitializer<Void> getRegisteredClientProvisioningPipeInit(RegisteredClient registeredClient){
        return PipelineInitializer.builder(()-> provisionRegisteredClient(registeredClient))
                .initialJobId("provision-registered-client")
                .pipelineId("provision-" + registeredClient.getClientName())
                .build();
    }

    private void logJobResults(LinkedList<JobResult<Void>> jobResults){
        log.info("Finished provisioning, results: " + jobResults);
    }

}
