package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;

import java.util.Collection;

public interface RedirectURIDataSourceRepository {

    Collection<RedirectURI> getRedirectURIByRegisteredClientIdCompanyCodeAndDivision(TSID registeredClientId, String companyCode, String division);

    void saveAll(Collection<RedirectURI> redirectUris);

}
