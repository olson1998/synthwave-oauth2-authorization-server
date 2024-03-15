package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisteredClientPropertiesJpaRepositoryWrapper implements RegisteredClientDataSourceRepository {

    private final RegisteredClientPropertiesJpaRepository registeredClientPropertiesJpaRepository;
}
