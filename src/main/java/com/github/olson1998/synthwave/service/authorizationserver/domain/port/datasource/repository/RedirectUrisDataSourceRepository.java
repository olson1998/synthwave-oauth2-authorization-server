package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;

import java.util.Collection;

public interface RedirectUrisDataSourceRepository {

    Collection<RedirectUri> getAllRedirectUris();
}
