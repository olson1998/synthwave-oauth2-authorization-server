package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.TokenSettingsEntity;

public interface TokenSettingsDataSourceRepository {

    void save(TokenSettingsEntity tokenSettings);
}
