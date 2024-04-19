package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.RedirectService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.ScopeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2RepositoryConfig {

    @Bean
    public RedirectRepository redirectRepository(RedirectUriDataSourceRepository redirectUriDataSourceRepository) {
        return new RedirectService(redirectUriDataSourceRepository);
    }

    @Bean
    public ScopeRepository scopeRepository(ScopeDataSourceRepository scopeDataSourceRepository) {
        return new ScopeService(scopeDataSourceRepository);
    }
}
