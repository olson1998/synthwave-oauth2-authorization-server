package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.AuthorityDataSourceRepositoryProxy;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.RedirectUrisDataSourceRepositoryProxy;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.RegisteredClientPropertiesSourceRepositoryProxy;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.UserPropertiesDataSourceRepositoryProxy;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserPropertiesSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveUserDetailsRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.SynthWaveRegisteredClientService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.SynthWaveUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2RepositoriesConfig {

    @Bean
    public SynthWaveRegisteredClientRepository synthWaveRegisteredClientRepository(UserPropertiesDataSourceRepositoryProxy userPropertiesSourceRepository,
                                                                                   RedirectUrisDataSourceRepositoryProxy redirectUrisDataSourceRepositoryProxy,
                                                                                   RegisteredClientPropertiesSourceRepositoryProxy synthWaveRegisteredClientDataSourceRepositoryProxy){
        return new SynthWaveRegisteredClientService(
                redirectUrisDataSourceRepositoryProxy,
                userPropertiesSourceRepository,
                synthWaveRegisteredClientDataSourceRepositoryProxy
        );
    }

    @Bean
    public SynthWaveUserDetailsRepository synthWaveUserDetailsRepository(UserPropertiesDataSourceRepositoryProxy synthWaveUserDataSourceRepositoryProxy,
                                                                         AuthorityDataSourceRepositoryProxy authorityDataSourceRepositoryProxy){
        return new SynthWaveUserDetailsService(
                synthWaveUserDataSourceRepositoryProxy,
                authorityDataSourceRepositoryProxy
        );
    }
}
