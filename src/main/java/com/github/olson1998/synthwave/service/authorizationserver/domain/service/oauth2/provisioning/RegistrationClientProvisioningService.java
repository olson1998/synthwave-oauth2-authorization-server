package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.exception.RegistrationClientProvisioningException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegisteredClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientProvisioningRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning.RegistrationClientRequestSupplier;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.RegistrationClientProvisioningRequest;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import com.github.olson1998.synthwave.support.pipeline.JobResult;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.PipelineInitializer;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class RegistrationClientProvisioningService implements RegistrationClientProvisioningRepository {

    private final RegistrationClientRequestSupplier registrationClientRequestSupplier;

    private final UserDetailsRepository userDetailsRepository;

    private final RegisteredClientProvisioningRepository registeredClientProvisioningRepository;

    @Override
    public void provision() {
        var request = registrationClientRequestSupplier.get();
        request.forEach(this::provisionUserOptionally);
        request.forEach(req -> registeredClientProvisioningRepository.provision(req.getRegisteredClient()));
    }

    @Override
    public CompletableFuture<Void> provisionAsync() {
        LinkedHashSet<RegistrationClientProvisioningRequest> registrationClientProvisioningRequests;
        try{
            registrationClientProvisioningRequests = registrationClientRequestSupplier.get();
        }catch (Exception e){
            log.error("Caught exception while getting registration client requests:",e);
            registrationClientProvisioningRequests = new LinkedHashSet<>();
        }
        var provisionRegistrationUserAsyncList = registrationClientProvisioningRequests.stream()
                .map(request -> Pipeline.initialJob(initializeRegistrationClientPipeline(request)))
                .map(pipeline -> pipeline.thenAcceptJob(this::provisionRegisteredClient, "provision-registered-client"))
                .toList();
        return Pipeline.combinePipelines(provisionRegistrationUserAsyncList)
                .thenAcceptJob(this::logRegistrationClientProvisionResult)
                .toFuture();
    }

    private PipelineInitializer<RegistrationClientProvisioningRequest> initializeRegistrationClientPipeline(RegistrationClientProvisioningRequest request){
        var username = Optional.ofNullable(request.getRegisteredClient())
                .map(RegisteredClient::getClientName)
                .orElse("?");
        return PipelineInitializer.builder(()-> provisionUserOptionally(request))
                .pipelineId("provision-" + username)
                .initialJobId("provision-user")
                .onInitialJobErrorHandler(error -> handleUserSavingError(error, request))
                .build();
    }

    private RegistrationClientProvisioningRequest provisionUserOptionally(RegistrationClientProvisioningRequest request){
        request.getOptionalUserSavingRequest().ifPresent(this::executeUserSavingRequest);
        return request;
    }

    private void provisionRegisteredClient(RegistrationClientProvisioningRequest registrationClientProvisioningRequest){
        Optional.ofNullable(registrationClientProvisioningRequest).ifPresentOrElse(request -> {
            var registeredClient = request.getRegisteredClient();
            registeredClientProvisioningRepository.provision(registeredClient);
        }, ()-> log.error("Aborting provisioning of registered client..."));
    }

    private void executeUserSavingRequest(@NonNull UserSavingRequest userSavingRequest){
        var username = userSavingRequest.getUser().getUsername();
        if(!userDetailsRepository.existsUserDetailsForUsername(username)){
            userDetailsRepository.saveUser(userSavingRequest);
            log.debug("Provisioned user: '{}'", username);
        }else {
            log.error("User with username: '{}' already exists!", username);
            throw new RegistrationClientProvisioningException("Failed to provision registered client");
        }
    }

    private void logRegistrationClientProvisionResult(LinkedList<JobResult<Void>> jobResultLinkedList){
        log.info("Finished async registration client provisioning, result: {}", jobResultLinkedList);
    }

    private RegistrationClientProvisioningRequest handleUserSavingError(PipelineJobFailure pipelineJobFailure,
                                                                        RegistrationClientProvisioningRequest request){
        log.error("Failed to process request: {}, reason", request.getOptionalUserSavingRequest(), pipelineJobFailure);
        return null;
    }

}
