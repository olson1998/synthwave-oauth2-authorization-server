package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserSchemaRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j

@Component
@RequiredArgsConstructor
public class AdminProvisioning {

    @Value("""
           #{
           objectMapper.readValue(
           T(org.springframework.util.ResourceUtils).getFile('${synthwave.authorizationserver.admin.provision.file}'),
           objectMapper.typeFactory.constructCollectionType(T(java.util.ArrayList),T(com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserSchema))
           )
           }
           """)
    private List<UserSchema> adminUserSchemas;

    private final UserSchemaRepository userSchemaRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void provisionAdmins(){
        Optional.ofNullable(adminUserSchemas).ifPresent(userSchemas -> {
            userSchemas.forEach(this::provisionAdminAsync);
        });
    }

    private void provisionAdminAsync(UserSchema userSchema){
        Pipeline.initialJob(()-> userSchemaRepository.existsSchema(userSchema))
                .thenRunJob(isExisting -> provisionAdmin(userSchema, isExisting), error -> handleProvisionError(userSchema, error))
                .thenAcceptJob(this::logAdminProvisioned);
    }

    private TSID provisionAdmin(UserSchema userSchema, boolean existsSchema){
        if(!existsSchema){
            return userSchemaRepository.save(userSchema);
        }else {
            log.info("Schema for user: {} already exists", userSchema.getProperties());
            return null;
        }
    }

    private TSID handleProvisionError(UserSchema userSchema, PipelineJobFailure pipelineJobFailure){
        log.error("Failed to provision: {}, reason:", userSchema.getProperties(), pipelineJobFailure);
        return null;
    }

    private void logAdminProvisioned(TSID userId){
        Optional.ofNullable(userId).ifPresent(id ->{
            log.info("Created admin, userId: '{}'", id.toLong());
        });
    }

}
