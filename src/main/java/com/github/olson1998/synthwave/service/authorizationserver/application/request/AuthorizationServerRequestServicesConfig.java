package com.github.olson1998.synthwave.service.authorizationserver.application.request;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.RedirectJpaRepositoryProxy;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectURIRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserPropertiesRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.UserRequestRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service.RedirectRequestService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service.UserRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationServerRequestServicesConfig {

    @Bean
    public UserRequestRepository userRequestRepository(UserPropertiesRepository userPropertiesRepository){
        return new UserRequestService(userPropertiesRepository);
    }

    @Bean
    public RedirectURIRepository redirectURIRepository(RedirectJpaRepositoryProxy redirectJpaRepositoryProxy){
        return new RedirectRequestService(redirectJpaRepositoryProxy);
    }
}
