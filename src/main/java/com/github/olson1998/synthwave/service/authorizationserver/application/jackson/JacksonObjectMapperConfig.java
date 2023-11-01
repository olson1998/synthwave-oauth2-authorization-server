package com.github.olson1998.synthwave.service.authorizationserver.application.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.json.AuthorizationServerMappingModule;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.json.AuthorizationServerMappingModuleImpl;
import com.github.olson1998.synthwave.support.springbootstarter.jackson.config.AbstractObjectMapperConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonObjectMapperConfig extends AbstractObjectMapperConfig {

    @Bean
    public AuthorizationServerMappingModule authorizationServerMappingModule(){
        return new AuthorizationServerMappingModuleImpl();
    }

    @Bean
    public ObjectMapper objectMapper(AuthorizationServerMappingModule authorizationServerMappingModule){
        return initObjectMapper(authorizationServerMappingModule::getModule);
    }
}
