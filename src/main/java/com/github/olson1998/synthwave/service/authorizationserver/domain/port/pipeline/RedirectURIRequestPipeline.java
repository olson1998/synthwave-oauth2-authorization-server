package com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RedirectURIRequestPipeline {

    CompletableFuture<Void> runRedirectURISavingPipeline(List<RedirectURI> redirectURIs);

    CompletableFuture<Void> runRedirectURIDeletingPipeline(List<RedirectURI> redirectURIS);
}
