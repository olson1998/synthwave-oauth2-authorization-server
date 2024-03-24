package com.github.olson1998.synthwave.service.authorizationserver.application.role.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role.RoleBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.role.RoleDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.role.RoleRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.role.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleRepositoryConfig {

    @Bean
    public RoleRepository roleRepository(RoleDataSourceRepository roleDataSourceRepository,
                                         RoleBindingDataSourceRepository roleBindingDataSourceRepository) {
        return new RoleService(roleDataSourceRepository, roleBindingDataSourceRepository);
    }

}
