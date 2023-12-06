package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;

import java.util.Collection;
import java.util.Set;

public interface RedirectRepository {

    Collection<RedirectEntity> saveAll(Collection<Redirect> redirectsCollection);

    void saveAllBindings(TSID registeredClientId, Set<String> redirectURISet, Set<String> postLogoutRedirectURISet);
}
