package com.github.olson1998.synthwave.service.authorizationserver.application.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.user.ApplicationUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public ApplicationUserRepository applicationUserRepository(ApplicationUserDataSourceRepository applicationUserDataSourceRepository) {
        return new ApplicationUserService(applicationUserDataSourceRepository);
    }
}
