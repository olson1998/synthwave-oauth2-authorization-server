package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientSettingsData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RegisteredClientSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RegisteredClientSettingsJpaRepositoryProxy implements RegisteredClientSettingsDataSourceRepository {

    private final RegisteredClientSettingsJpaRepository registeredClientSettingsJpaRepository;

    @Override
    public void save(@NonNull RegisteredClientSettings registeredClientSettings) {
        var data = new RegisteredClientSettingsData(registeredClientSettings);
        registeredClientSettingsJpaRepository.save(data);
    }

    @Override
    public void saveAll(@NonNull Collection<RegisteredClientSettings> registeredClientSettings) {
        var data = registeredClientSettings.stream()
                .map(RegisteredClientSettingsData::new)
                .toList();
        registeredClientSettingsJpaRepository.saveAll(data);
    }
}
