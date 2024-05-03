package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationGrantTypeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ClientAuthenticationMethodRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.AuthorizationGrantTypeService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.ClientAuthenticationMethodService;
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

    @Bean
    public AuthorizationGrantTypeRepository authorizationGrantTypeRepository(AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository) {
        return new AuthorizationGrantTypeService(authorizationGrantTypeDatasourceRepository);
    }

    @Bean
    public ClientAuthenticationMethodRepository clientAuthenticationMethodRepository(ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository) {
        return new ClientAuthenticationMethodService(clientAuthenticationMethodDataSourceRepository);
    }

}
