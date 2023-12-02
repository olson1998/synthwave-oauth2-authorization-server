package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;

public interface RegisteredClientTokenSettingsDataSourceRepository {

    void save(RegisteredClientTokenSettings registeredClientTokenSettings);
}
