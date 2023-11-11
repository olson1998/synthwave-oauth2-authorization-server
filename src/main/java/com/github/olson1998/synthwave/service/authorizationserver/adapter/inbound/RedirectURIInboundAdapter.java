package com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectURIsRequestPipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Async(ASYNC_TASK_EXEC)
@RestController
@RequestMapping(path = "/redirect-uri")

@RequiredArgsConstructor
public class RedirectURIInboundAdapter {

    private final RedirectURIsRequestPipeline redirectURIsRequestPipeline;

    @ResponseStatus(CREATED)
    @PostMapping(path = "/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CompletableFuture<Void> postRedirectURI(@RequestBody List<RedirectURI> redirectURIList){
        return redirectURIsRequestPipeline.runRedirectURIsSavingPipeline(redirectURIList);
    }

    @ResponseStatus(ACCEPTED)
    @PostMapping(path = "/delete", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CompletableFuture<Void> deleteRedirectURI(@RequestBody List<RedirectURI> redirectURIList){
        return redirectURIsRequestPipeline.runRedirectURIsSavingPipeline(redirectURIList);
    }
}
