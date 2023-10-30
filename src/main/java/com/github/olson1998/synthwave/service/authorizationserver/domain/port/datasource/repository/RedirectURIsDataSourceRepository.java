package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;

import java.util.Collection;

public interface RedirectURIsDataSourceRepository {

    Collection<RedirectURI> getAllRedirectUris();

    Collection<RedirectURI> getAllNotPresentRedirectUris(Collection<RedirectURI> redirectUris);

    void saveAll(Collection<RedirectURI> redirectUris);
}
