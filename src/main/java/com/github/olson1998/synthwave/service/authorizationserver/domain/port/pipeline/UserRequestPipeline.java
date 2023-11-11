package com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import io.hypersistence.tsid.TSID;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;

@Async(ASYNC_TASK_EXEC)
public interface UserRequestPipeline {

    CompletableFuture<Map<String, String>> runSavingUserPipeline(UserSchema userSchema);

    CompletableFuture<Void> runSavingActivatedUserPipeline(UserSchema userSchema);

    CompletableFuture<Void> runUserActivationPipeline(String activationToken);

    CompletableFuture<Void> runUserDeactivationPipeline(TSID userId);
}
