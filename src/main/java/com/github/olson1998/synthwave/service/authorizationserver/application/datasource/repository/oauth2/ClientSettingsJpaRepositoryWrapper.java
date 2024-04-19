package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientSettingsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientSettingsJpaRepositoryWrapper implements ClientSettingsDataSourceRepository {

    private final ClientSettingsJpaRepository clientSettingsJpaRepository;

    @Override
    public void save(ClientSettingsEntity clientSettings) {

    }
}
