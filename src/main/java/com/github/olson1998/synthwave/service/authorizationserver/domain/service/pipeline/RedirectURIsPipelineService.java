package com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectURIRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectURIRepository;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import com.github.olson1998.synthwave.support.rest.exception.InternalServerErrorWebException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class RedirectURIsPipelineService implements RedirectURIRequestPipeline {

    private final RedirectURIRepository redirectURIRepository;

    @Override
    public CompletableFuture<Void> runRedirectURISavingPipeline(List<RedirectURI> redirectURIs) {
        return Pipeline.initialJob(()-> saveRedirectURIs(redirectURIs), this::handleSavingUriError)
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> runRedirectURIDeletingPipeline(List<RedirectURI> redirectURIS) {
        return Pipeline.initialJob(()-> deleteRedirectURIs(redirectURIS), this::handleDeletingUriError)
                .toFuture();
    }

    private Void saveRedirectURIs(List<RedirectURI> redirectURIs){
        redirectURIRepository.saveAll(redirectURIs);
        return null;
    }

    private Void deleteRedirectURIs(List<RedirectURI> redirectURIs){
        redirectURIRepository.saveAll(redirectURIs);
        return null;
    }

    private Void handleSavingUriError(PipelineJobFailure pipelineJobFailure){
        log.error("Failed to save redirect URI, reason:", pipelineJobFailure);
        throw new InternalServerErrorWebException(pipelineJobFailure, false);
    }

    private Void handleDeletingUriError(PipelineJobFailure pipelineJobFailure){
        log.error("Failed to delete redirect URI, reason:", pipelineJobFailure);
        throw new InternalServerErrorWebException(pipelineJobFailure, false);
    }
}
