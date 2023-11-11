package com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.UserRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Async(ASYNC_TASK_EXEC)
@RestController
@RequestMapping("/user")

@RequiredArgsConstructor
public class UserPropertiesInboundAdapter {

    private final UserRequestPipeline userRequestPipeline;

    @ResponseStatus(CREATED)
    @PostMapping(path = "/save", consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public CompletableFuture<Map<String, String>> postUserSaveRequest(@RequestBody UserSchema userSchema){
        return userRequestPipeline.runSavingUserPipeline(userSchema);
    }

    @ResponseStatus(ACCEPTED)
    @GetMapping(path = "/activate")
    public CompletableFuture<Void> getUserActivation(@RequestParam("user") String activationToken){
        return userRequestPipeline.runUserActivationPipeline(activationToken);
    }
}
