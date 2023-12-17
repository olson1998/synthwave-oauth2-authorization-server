package com.github.olson1998.synthwave.service.authorizationserver.application.databind.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.databind.AuthorizationServerModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.AuthorizationServerModuleImpl;
import com.github.olson1998.synthwave.support.springbootstarter.jackson.config.AbstractObjectMapperConfig;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JacksonObjectMapperConfig extends AbstractObjectMapperConfig {

    @Bean
    public AuthorizationServerModule authorizationServerMappingModule(){
        return new AuthorizationServerModuleImpl();
    }

    @Bean
    public ObjectMapper objectMapper(@NonNull AuthorizationServerModule authorizationServerModule){
        return initObjectMapper(List.of(authorizationServerModule::getModule));
    }
}
