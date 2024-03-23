package com.github.olson1998.synthwave.service.authorizationserver.application.authority.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.authority.AuthorityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorityRepositoryConfig {

    @Bean
    public AuthorityRepository authorityRepository(AuthorityDataSourceRepository authorityDataSourceRepository,
                                                   AuthorityBindingDataSourceRepository authorityBindingDataSourceRepository) {
        return new AuthorityService(authorityDataSourceRepository, authorityBindingDataSourceRepository);
    }

}
