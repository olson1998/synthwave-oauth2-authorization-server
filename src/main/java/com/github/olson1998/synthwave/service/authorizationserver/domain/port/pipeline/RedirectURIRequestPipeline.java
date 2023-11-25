package com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RedirectURIRequestPipeline {

    CompletableFuture<Void> runRedirectURISavingPipeline(List<Redirect> redirectURIses);

    CompletableFuture<Void> runRedirectURIDeletingPipeline(List<Redirect> redirectURISES);
}
