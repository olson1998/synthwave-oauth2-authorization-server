package com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.UserRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound.UserPropertiesInboundAdapter.USER_REQUEST_PATH;
import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Async(ASYNC_TASK_EXEC)
@RestController
@RequestMapping(USER_REQUEST_PATH)

@RequiredArgsConstructor
public class UserPropertiesInboundAdapter {

    public static final String USER_REQUEST_PATH = "/user";

    public static final String SAVE_USER_REQUEST_PATH = "/save";

    public static final String ACTIVATE_USER_REQUEST_PATH = "/activate";

    private final UserRequestPipeline userRequestPipeline;

    @ResponseStatus(CREATED)
    @PostMapping(path = SAVE_USER_REQUEST_PATH, consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public CompletableFuture<Map<String, String>> postUserSaveRequest(@RequestBody UserSchema userSchema){
        return userRequestPipeline.runSavingUserPipeline(userSchema);
    }

    @ResponseStatus(ACCEPTED)
    @GetMapping(path = ACTIVATE_USER_REQUEST_PATH)
    public CompletableFuture<Void> getUserActivation(@RequestParam("user") String activationToken){
        return userRequestPipeline.runUserActivationPipeline(activationToken);
    }
}
