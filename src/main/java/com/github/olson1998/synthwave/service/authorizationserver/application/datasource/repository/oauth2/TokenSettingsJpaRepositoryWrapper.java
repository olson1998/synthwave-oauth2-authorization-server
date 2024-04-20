package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.TokenSettingsData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.TokenSettingsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.TokenSettingsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenSettingsJpaRepositoryWrapper implements TokenSettingsDataSourceRepository {

    private final TokenSettingsJpaRepository tokenSettingsJpaRepository;

    @Override
    public void save(TokenSettingsEntity tokenSettings) {
        var data = new TokenSettingsData(tokenSettings);
        tokenSettingsJpaRepository.save(data);
    }
}
