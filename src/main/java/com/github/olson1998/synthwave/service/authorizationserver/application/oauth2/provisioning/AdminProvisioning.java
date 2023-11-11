package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.request.exception.UserExistsException;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.UserRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserRequestRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import com.github.olson1998.sythwave.support.jackson.exception.IORuntimeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;

@Slf4j

@Component
public class AdminProvisioning {

    private final String adminUserSchemasConfigFile;

    private final ObjectMapper objectMapper;

    private final UserRequestPipeline userRequestPipeline;

    private final UserRequestRepository userRequestRepository;

    public AdminProvisioning(@Value("${synthwave.authorizationserver.admin.provision.file:#{null}}") String adminUserSchemasConfigFile,
                             ObjectMapper objectMapper,
                             UserRequestPipeline userRequestPipeline,
                             UserRequestRepository userRequestRepository) {
        this.adminUserSchemasConfigFile = adminUserSchemasConfigFile;
        this.objectMapper = objectMapper;
        this.userRequestPipeline = userRequestPipeline;
        this.userRequestRepository = userRequestRepository;
    }

    @Async(ASYNC_TASK_EXEC)
    @EventListener(ApplicationStartedEvent.class)
    public CompletableFuture<Void> provisionAdmins(){
        return Pipeline.initialJob(()-> getJSONConfigFile(adminUserSchemasConfigFile), error -> handleGetFileError(adminUserSchemasConfigFile, error))
                .thenRunJob(this::deserializeJSONConfig, this::handleDeserializationError)
                .thenAcceptJob(this::provisionAdminsAsync)
                .toFuture();
    }

    private void provisionAdminsAsync(Collection<UserSchema> userSchemaCollection){
        Optional.ofNullable(userSchemaCollection).ifPresent(schemas -> schemas.forEach(schema ->{
            Pipeline.ofCompletableFuture(userRequestPipeline.runSavingUserPipeline(schema))
                    .thenRunJob(tokens -> tokens.get("activation_token"))
                    .thenAcceptJob(this::activateAdmin, this::handleActivationError);
        }));
    }

    private Map<String, String> provisionAdmin(UserSchema userSchema){
        return userRequestRepository.saveUser(userSchema);
    }

    private void activateAdmin(String activationToken){
        Objects.requireNonNull(activationToken);
        userRequestRepository.activateUser(activationToken);
    }

    private File getJSONConfigFile(String filePath){
        return Optional.ofNullable(filePath)
                .map(path -> {
                    try {
                        return ResourceUtils.getFile(path);
                    } catch (FileNotFoundException e) {
                        throw new IORuntimeException(e);
                    }
                }).orElse(null);
    }

    @SneakyThrows
    private List<UserSchema> deserializeJSONConfig(File configFile){
        return Optional.ofNullable(configFile)
                .map(file ->{
                    try {
                        return objectMapper.readValue(configFile, new TypeReference<List<UserSchema>>() {
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).orElse(null);
    }

    private Map<String, String> handleProvisionError(UserSchema userSchema, PipelineJobFailure pipelineJobFailure){
        var cause = pipelineJobFailure.getCause();
        if(cause == null){
            log.error("Failed to provision: {}, reason:", userSchema.getUser(), pipelineJobFailure);
        }else if (!(cause instanceof UserExistsException)){
            log.error("Failed to provision: {}, reason:", userSchema.getUser(), pipelineJobFailure);
        }
        throw new IllegalStateException("Cannot continue running pipeline");
    }

    private File handleGetFileError(String filePath, PipelineJobFailure pipelineJobFailure){
        log.error("Failed to get file: {}, reason:", filePath, pipelineJobFailure);
        throw new IllegalStateException("Cannot continue running pipeline");
    }

    private List<UserSchema> handleDeserializationError(PipelineJobFailure pipelineJobFailure){
        log.error("Failed to deserialize config file, reason:", pipelineJobFailure);
        throw new IllegalStateException("Cannot continue running pipeline");
    }

    private Void handleActivationError(PipelineJobFailure pipelineJobFailure){
        log.error("Failed to activate admin, reason:", pipelineJobFailure);
        throw new IllegalStateException("Cannot continue running pipeline");
    }

}
