package com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectURIRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectRepository;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.exception.PipelineJobFailure;
import com.github.olson1998.synthwave.support.web.exception.InternalServerErrorWebException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class RedirectPipelineService implements RedirectURIRequestPipeline {

    private final RedirectRepository redirectRepository;

    @Override
    public CompletableFuture<Void> runRedirectURISavingPipeline(List<Redirect> redirectSet) {
        return Pipeline.initialJob(()-> saveRedirectURIs(redirectSet), this::handleSavingUriError)
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> runRedirectURIDeletingPipeline(List<Redirect> redirectSet) {
        return Pipeline.initialJob(()-> deleteRedirectURIs(redirectSet), this::handleDeletingUriError)
                .toFuture();
    }

    private Void saveRedirectURIs(List<Redirect> redirectURIses){
        log.debug("Saving redirect URI");
        redirectRepository.saveAll(redirectURIses);
        return null;
    }

    private Void deleteRedirectURIs(List<Redirect> redirectURIses){
        log.debug("Deleting redirect URI");
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
