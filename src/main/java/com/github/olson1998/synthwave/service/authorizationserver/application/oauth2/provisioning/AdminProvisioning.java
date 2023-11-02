package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.provisioning;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserSchemaRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import com.github.olson1998.sythwave.support.jackson.exception.IORuntimeException;
import io.hypersistence.tsid.TSID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j

@Component
public class AdminProvisioning {

    private final String adminUserSchemasConfigFile;

    private final ObjectMapper objectMapper;

    private final UserSchemaRepository userSchemaRepository;

    public AdminProvisioning(@Value("${synthwave.authorizationserver.admin.provision.file:#{null}}") String adminUserSchemasConfigFile,
                             ObjectMapper objectMapper,
                             UserSchemaRepository userSchemaRepository) {
        this.adminUserSchemasConfigFile = adminUserSchemasConfigFile;
        this.objectMapper = objectMapper;
        this.userSchemaRepository = userSchemaRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void provisionAdmins(){
        Pipeline.initialJob(()-> getJSONConfigFile(adminUserSchemasConfigFile), error -> handleGetFileError(adminUserSchemasConfigFile, error))
                .thenRunJob(this::deserializeJSONConfig, this::handleDeserializationError)
                .thenAcceptJob(this::provisionAdminsAsync);
    }

    private void provisionAdminsAsync(Collection<UserSchema> userSchemaCollection){
        Optional.ofNullable(userSchemaCollection).ifPresent(schemas -> schemas.forEach(this::provisionAdminAsync));
    }

    private void provisionAdminAsync(UserSchema userSchema){
        Pipeline.initialJob(()-> userSchemaRepository.existsSchema(userSchema), error -> handleExistsError(userSchema, error))
                .thenRunJob(isExisting -> provisionAdmin(userSchema, isExisting), error -> handleProvisionError(userSchema, error))
                .thenAcceptJob(this::logAdminProvisioned);
    }

    private TSID provisionAdmin(UserSchema userSchema, Boolean existsSchema){
        if(existsSchema != null){
            if(!existsSchema){
                return userSchemaRepository.save(userSchema);
            }else {
                log.info("Schema for user: {} already exists", userSchema.getUser());
                return null;
            }
        }else {
            return null;
        }
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

    private Boolean handleExistsError(UserSchema userSchema, PipelineJobFailure pipelineJobFailure){
        log.error("Failed to predicate if '{}' exists: {}, reason:", userSchema.getUser(), pipelineJobFailure);
        return null;
    }

    private TSID handleProvisionError(UserSchema userSchema, PipelineJobFailure pipelineJobFailure){
        log.error("Failed to provision: {}, reason:", userSchema.getUser(), pipelineJobFailure);
        return null;
    }

    private File handleGetFileError(String filePath, PipelineJobFailure pipelineJobFailure){
        log.error("Failed to get file: {}, reason:", filePath, pipelineJobFailure);
        return null;
    }

    private List<UserSchema> handleDeserializationError(PipelineJobFailure pipelineJobFailure){
        log.error("Failed to deserialize config file, reason:", pipelineJobFailure);
        return null;
    }

    private void logAdminProvisioned(TSID userId){
        Optional.ofNullable(userId).ifPresent(id -> log.info("Created admin, userId: '{}'", id.toLong()));
    }

}
