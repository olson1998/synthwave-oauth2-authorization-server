package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthorizationProperties;

import java.util.Optional;

public interface OAuth2AuthorizationDataSourceRepository {

    Optional<AuthorizationProperties> getAuthorizationPropertiesById(String id);

    Optional<AuthorizationProperties> getAuthorizationPropertiesByTokenAndTokenClass(String token, Class<?> tokenClass);

    void save(AuthorizationProperties oAuth2AuthorizationProperties);

    int delete(AuthorizationProperties oAuth2Authorization);
}
