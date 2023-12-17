package com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectPipelineRepository;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;
import com.github.olson1998.synthwave.support.pipeline.PipelineExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class RedirectPipelineService implements RedirectPipelineRepository {

    private final PipelineExecutor pipelineExecutor;

    private final RedirectRepository redirectRepository;

    @Override
    public Pipeline<Collection<RedirectEntity>> saveAll(Collection<Redirect> redirectCollection) {
        return pipelineExecutor.createPipeline(()-> redirectRepository.saveAll(redirectCollection), null, "save-redirects");
    }

}
