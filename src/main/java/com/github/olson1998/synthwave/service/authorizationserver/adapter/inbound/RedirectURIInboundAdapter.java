package com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectURIRequestPipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.service.authorizationserver.adapter.inbound.RedirectURIInboundAdapter.REDIRECT_URI_REQUEST_PATH;
import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Async(ASYNC_TASK_EXEC)
@RestController
@RequestMapping(path = REDIRECT_URI_REQUEST_PATH)

@RequiredArgsConstructor
public class RedirectURIInboundAdapter {

    public static final String REDIRECT_URI_REQUEST_PATH = "/redirect-uri";

    public static final String CREATE_REDIRECT_URI_ENDPOINT = "/save";

    public static final String DELETE_REDIRECT_URI_ENDPOINT = "/delete";

    private final RedirectURIRequestPipeline redirectURIRequestPipeline;

    @ResponseStatus(CREATED)
    @PostMapping(path = CREATE_REDIRECT_URI_ENDPOINT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CompletableFuture<Void> postSaveRedirectURI(@RequestBody List<RedirectURI> redirectURIList){
        return redirectURIRequestPipeline.runRedirectURISavingPipeline(redirectURIList);
    }

    @ResponseStatus(ACCEPTED)
    @PostMapping(path = DELETE_REDIRECT_URI_ENDPOINT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CompletableFuture<Void> deleteRedirectURI(@RequestBody List<RedirectURI> redirectURIList){
        return redirectURIRequestPipeline.runRedirectURIDeletingPipeline(redirectURIList);
    }
}
