package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;

import java.util.Collection;
import java.util.Set;

public interface RedirectURIDataSourceRepository {

    Collection<RedirectURIEntity> getRedirectURIByRegisteredClientId(TSID registeredClientId);

    Collection<TSID> getRedirectURIIdCollectionByRedirectURISetAndPostLogoutRedirectURISet(Set<String> redirectURISet,
                                                                                           Set<String> postLogoutRedirectURISet);

    void saveAll(Collection<RedirectURI> redirectUris);

}
