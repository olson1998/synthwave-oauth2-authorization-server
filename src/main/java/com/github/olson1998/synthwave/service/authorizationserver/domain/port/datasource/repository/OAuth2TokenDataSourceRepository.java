package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.TokenProperties;

import java.util.Collection;

public interface OAuth2TokenDataSourceRepository {

    Collection<TokenProperties> getTokensByAuthorizationId(String authorizationId);

    void saveAll(Collection<TokenProperties> tokenPropertiesCollection);

    int deleteAllWithAuthorizationId(String authorizationId);
}
