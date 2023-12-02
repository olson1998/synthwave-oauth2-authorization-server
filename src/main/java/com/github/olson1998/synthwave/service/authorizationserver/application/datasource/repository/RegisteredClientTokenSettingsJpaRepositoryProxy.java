package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientTokenSettingsData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientTokenSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisteredClientTokenSettingsJpaRepositoryProxy implements RegisteredClientTokenSettingsDataSourceRepository {

    private final RegisteredClientTokenSettingsJpaRepository registeredClientTokenSettingsJpaRepository;

    @Override
    public void save(RegisteredClientTokenSettings registeredClientTokenSettings) {
        var data = new RegisteredClientTokenSettingsData(registeredClientTokenSettings);
        registeredClientTokenSettingsJpaRepository.save(data);
    }
}
