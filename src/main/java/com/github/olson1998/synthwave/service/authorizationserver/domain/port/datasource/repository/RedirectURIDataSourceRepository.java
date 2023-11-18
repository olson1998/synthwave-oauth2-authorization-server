package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;

import java.util.Collection;
import java.util.Set;

public interface RedirectURIDataSourceRepository {

    Collection<RedirectURIEntity> getRedirectURIByURISet(Set<String> uriSet);

    Collection<RedirectURIEntity> getPostLogoutRedirectURIIdByURISet(Set<String> uriSet);

    Collection<RedirectURI> getRedirectURIByRegisteredClientIdCompanyCodeAndDivision(TSID registeredClientId, String companyCode, String division);

    void saveAll(Collection<RedirectURI> redirectUris);

}
