package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;

import java.util.Collection;
import java.util.Set;

public interface RedirectDataSourceRepository {

    Collection<RedirectEntity> getRedirectByRegisteredClientId(TSID registeredClientId);

    Collection<RedirectEntity> getRedirectFromURISet(Set<String> redirectURISet, Set<String> postLogoutRedirectURISet);

    RedirectEntity save(Redirect redirect);

    Collection<RedirectEntity> saveAll(Collection<Redirect> redirectSet);

}
