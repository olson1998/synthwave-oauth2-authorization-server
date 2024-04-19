package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientSecretData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientSecretDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RegisteredClientSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisteredClientSecretJpaRepositoryWrapper implements RegisteredClientSecretDataSourceRepository {

    private final RegisteredClientSecretJpaRepository registeredClientSecretJpaRepository;

    @Override
    public void save(RegisteredClientSecret registeredClientSecret) {
        registeredClientSecretJpaRepository.save(new RegisteredClientSecretData(registeredClientSecret));
    }
}
