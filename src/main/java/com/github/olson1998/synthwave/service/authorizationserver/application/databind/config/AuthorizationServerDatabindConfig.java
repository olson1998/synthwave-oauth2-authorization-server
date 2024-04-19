package com.github.olson1998.synthwave.service.authorizationserver.application.databind.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.service.databind.AuthorizationServerObjectMapperConfigurer;
import com.github.olson1998.synthwave.support.springbootstarter.service.ObjectMapperConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationServerDatabindConfig {

    @Bean
    public ObjectMapperConfigurer objectMapperConfigurer() {
        return new AuthorizationServerObjectMapperConfigurer();
    }
}
