package com.github.olson1998.synthwave.service.authorizationserver.application.user.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.ApplicationUserDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.user.UserPasswordDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.role.RoleRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.ApplicationUserRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.user.repository.UserPasswordRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.user.ApplicationUserService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.user.UserPasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserServiceConfig {

    @Bean
    public ApplicationUserRepository applicationUserRepository(UserPasswordRepository userPasswordRepository,
                                                               RoleRepository roleRepository,
                                                               AuthorityRepository authorityRepository,
                                                               ApplicationUserDataSourceRepository applicationUserDataSourceRepository) {
        return new ApplicationUserService(
                userPasswordRepository,
                roleRepository,
                authorityRepository,
                applicationUserDataSourceRepository
        );
    }

    @Bean
    public UserPasswordRepository userPasswordRepository(PasswordEncoder passwordEncoder,
                                                         UserPasswordDataSourceRepository userPasswordDataSourceRepository) {
        return new UserPasswordService(passwordEncoder, userPasswordDataSourceRepository);
    }
}
