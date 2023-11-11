package com.github.olson1998.synthwave.service.authorizationserver.application.pipeline;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectURIsRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.UserRequestPipeline;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectURIRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserRequestRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline.RedirectURIsPipelineService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline.UserRequestPipelineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationServicePipelineServicesConfig {

    @Bean
    public UserRequestPipeline userRequestPipeline(UserRequestRepository userRequestRepository){
        return new UserRequestPipelineService(userRequestRepository);
    }

    @Bean
    public RedirectURIsRequestPipeline redirectURIsRequestPipeline(RedirectURIRepository redirectURIRepository){
        return new RedirectURIsPipelineService(redirectURIRepository);
    }
}
