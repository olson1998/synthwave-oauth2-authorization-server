package com.github.olson1998.synthwave.service.authorizationserver.application.pipeline.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.pipeline.RedirectPipelineRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.pipeline.RedirectPipelineService;
import com.github.olson1998.synthwave.support.pipeline.PipelineExecutor;
import com.github.olson1998.synthwave.support.pipeline.PipelineExecutorService;
import org.apache.tomcat.util.threads.VirtualThreadExecutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class PipelineConfig {

    public static final String PIPELINE_VIRTUAL_THREAD_EXEC = "pipelineVirtualThreadExecutor";

    @Bean(PIPELINE_VIRTUAL_THREAD_EXEC)
    public Executor executor(){
        return new VirtualThreadExecutor("virtual-");
    }

    @Bean
    public PipelineExecutor pipelineExecutor(@Qualifier(PIPELINE_VIRTUAL_THREAD_EXEC) Executor executor){
        return new PipelineExecutorService(executor);
    }

    @Bean
    public RedirectPipelineRepository redirectPipelineRepository(PipelineExecutor pipelineExecutor,
                                                                 RedirectRepository redirectRepository){
        return new RedirectPipelineService(pipelineExecutor, redirectRepository);
    }
}
