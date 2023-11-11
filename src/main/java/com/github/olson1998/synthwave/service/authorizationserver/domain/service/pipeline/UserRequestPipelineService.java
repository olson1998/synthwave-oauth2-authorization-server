package com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.UserRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserRequestRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import com.github.olson1998.synthwave.support.rest.exception.InternalServerErrorWebException;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class UserRequestPipelineService implements UserRequestPipeline {

    private final UserRequestRepository userRequestRepository;

    @Override
    public CompletableFuture<Map<String, String>> runSavingUserPipeline(UserSchema userSchema) {
        return Pipeline.initialJob(()-> userRequestRepository.saveUser(userSchema), error -> handleSavingUserError(error, userSchema))
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> runSavingActivatedUserPipeline(UserSchema userSchema) {
        return Pipeline.initialJob(()-> userRequestRepository.saveUser(userSchema), error -> handleSavingUserError(error, userSchema))
                .thenRunJob(tokenMap -> tokenMap.get("activation_token"))
                .thenRunJob(userRequestRepository::activateUser, this::handleActivationError)
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> runUserActivationPipeline(String activationToken) {
        return Pipeline.initialJob(()-> userRequestRepository.activateUser(activationToken), this::handleActivationError)
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> runUserDeactivationPipeline(TSID userId) {
        return Pipeline.initialJob(()-> userRequestRepository.deactivateUser(userId), error -> handleDeactivationError(error, userId))
                .toFuture();
    }

    private Map<String, String> handleSavingUserError(PipelineJobFailure pipelineJobFailure, UserSchema userSchema){
        log.error("Failed to save user: '{}', reason:", userSchema.getUser().getUsername(), pipelineJobFailure);
        throw new InternalServerErrorWebException(pipelineJobFailure, false);
    }

    private Void handleActivationError(PipelineJobFailure pipelineJobFailure){
        log.error("Failed to activate user, reason:", pipelineJobFailure);
        throw new InternalServerErrorWebException(pipelineJobFailure, false);
    }

    private Void handleDeactivationError(PipelineJobFailure pipelineJobFailure, TSID userID){
        log.error("Failed to deactivate user: '{}', reason:",userID, pipelineJobFailure);
        throw new InternalServerErrorWebException(pipelineJobFailure, false);
    }
}
