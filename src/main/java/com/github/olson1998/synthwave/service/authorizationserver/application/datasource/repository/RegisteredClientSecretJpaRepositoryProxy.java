package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientSecretData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisteredClientSecretJpaRepositoryProxy implements RegisteredClientSecretDataSourceRepository {

    private final RegisteredClientSecretJpaRepository registeredClientSecretJpaRepository;

    @Override
    public void save(RegisteredClientSecret registeredClientSecret) {
        var data = new RegisteredClientSecretData(registeredClientSecret);
        registeredClientSecretJpaRepository.save(data);
    }
}
