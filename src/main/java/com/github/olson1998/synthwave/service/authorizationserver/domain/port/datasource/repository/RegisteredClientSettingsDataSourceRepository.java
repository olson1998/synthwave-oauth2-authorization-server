package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientSettings;

import java.util.Collection;

public interface RegisteredClientSettingsDataSourceRepository {

    void save(RegisteredClientSettings registeredClientSettings);

    void saveAll(Collection<RegisteredClientSettings> registeredClientSettings);
}
