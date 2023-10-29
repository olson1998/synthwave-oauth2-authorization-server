package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.OAuth2AuthorizationData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.OAuth2TokenDesc;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.OAuth2AuthorizationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthorizationJpaRepositoryProxy implements OAuth2AuthorizationDataSourceRepository {

    private final OAuth2AuthorizationJpaRepository oAuth2AuthorizationJpaRepository;

    @Override
    public Optional<AuthorizationProperties> getAuthorizationPropertiesById(@NonNull String id) {
        return oAuth2AuthorizationJpaRepository.selectAuthorizationById(id)
                .map(AuthorizationProperties.class::cast);
    }

    @Override
    public Optional<AuthorizationProperties> getAuthorizationPropertiesByTokenAndTokenClass(@NonNull String token, @NonNull Class<?> tokenClass) {
        return oAuth2AuthorizationJpaRepository.selectAuthorizationByTokenAndTokenClass(token, tokenClass)
                .map(AuthorizationProperties.class::cast);
    }

    @Override
    public void save(AuthorizationProperties oAuth2Authorization) {
        var authorizationData = new OAuth2AuthorizationData(oAuth2Authorization);
        oAuth2AuthorizationJpaRepository.save(authorizationData);
    }

    @Override
    public int delete(AuthorizationProperties oAuth2Authorization) {
        var authorizationData = new OAuth2AuthorizationData(oAuth2Authorization);
        return oAuth2AuthorizationJpaRepository.deleteAuthorizationByAuthorizationData(authorizationData);
    }
}
