package com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.olson1998.synthwave.support.springbootstarter.async.config.ThreadPoolConfig.ASYNC_TASK_EXEC;

@Async(ASYNC_TASK_EXEC)
public interface RedirectURIRequestPipeline {

    CompletableFuture<Void> runRedirectURISavingPipeline(List<RedirectURI> redirectURIs);

    CompletableFuture<Void> runRedirectURIDeletingPipeline(List<RedirectURI> redirectURIS);
}
