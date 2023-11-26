package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientRequestSupplier;
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
public class RegistrationClientProvisioningService implements RegistrationClientProvisioningRepository {

    private final RegistrationClientRequestSupplier registrationClientRequestSupplier;

    private final RegisteredClientProvisioningRepository registeredClientProvisioningRepository;

    @Override
    public void provision() {
        var registeredClientSet = registrationClientRequestSupplier.get();
        registeredClientSet.forEach(registeredClientProvisioningRepository::provision);
    }

    @Override
    public CompletableFuture<Void> provisionAsync() {
        LinkedHashSet<RegisteredClient> registeredClients;
        try{
            registeredClients = registrationClientRequestSupplier.get();
        }catch (Exception e){
            log.error("Failed to obtain registration client, reason:", e);
            registeredClients = new LinkedHashSet<>();
        }
        var provisioningPipes = registeredClients.stream()
                .map(this::getRegisteredClientProvisioningPipeInit)
                .map(Pipeline::initialJob)
                .toList();
        return Pipeline.combinePipelines(provisioningPipes)
                .thenAcceptJob(this::logJobResults)
                .toFuture();
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
