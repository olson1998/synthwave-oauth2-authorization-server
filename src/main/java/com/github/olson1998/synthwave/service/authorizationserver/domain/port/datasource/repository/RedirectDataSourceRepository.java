package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;

import java.util.Collection;
import java.util.Set;

public interface RedirectDataSourceRepository {

    Collection<RedirectEntity> getRedirectURIByRegisteredClientId(TSID registeredClientId);

    Collection<RedirectEntity> getRedirectURICollectionByRedirectURISetAndPostLogoutRedirectURISet(Set<String> redirectURISet,
                                                                                                   Set<String> postLogoutRedirectURISet);

    Collection<RedirectEntity> saveAll(Collection<Redirect> redirectUrises);

}
