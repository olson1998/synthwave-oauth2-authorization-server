package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.OAuth2TokenData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.OAuth2TokenDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class OAuth2TokenJpaRepositoryProxy implements OAuth2TokenDataSourceRepository {

    private final OAuth2TokenJpaRepository oAuth2TokenJpaRepository;

    @Override
    public Collection<TokenProperties> getTokensByAuthorizationId(@NonNull String authorizationId) {
        return oAuth2TokenJpaRepository.selectOAuth2TokensByAuthorizationId(authorizationId).stream()
                .map(TokenProperties.class::cast)
                .toList();
    }

    @Override
    public void saveAll(@NonNull Collection<TokenProperties> tokenPropertiesCollection) {
        var data = tokenPropertiesCollection.stream()
                .map(OAuth2TokenData::new)
                .toList();
        oAuth2TokenJpaRepository.saveAll(data);
    }

    @Override
    public int deleteAllWithAuthorizationId(String authorizationId) {
        return oAuth2TokenJpaRepository.deleteTokenByAuthorizationId(authorizationId);
    }

}
