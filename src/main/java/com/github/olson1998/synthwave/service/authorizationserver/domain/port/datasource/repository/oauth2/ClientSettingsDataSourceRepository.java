package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientSettingsEntity;

public interface ClientSettingsDataSourceRepository {

    void save(ClientSettingsEntity clientSettings);
}
