package com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.support.pipeline.Pipeline;

import java.util.Collection;

public interface RedirectPipelineRepository {

    Pipeline<Collection<RedirectEntity>> saveAll(Collection<Redirect> redirectCollection);
}
