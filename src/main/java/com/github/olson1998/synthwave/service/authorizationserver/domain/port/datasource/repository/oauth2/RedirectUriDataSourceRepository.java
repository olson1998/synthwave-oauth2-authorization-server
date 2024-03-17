package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.support.web.util.URIModel;

import java.util.Set;

public interface RedirectUriDataSourceRepository {

    Set<URIModel> getRedirectUriByRegisteredClientId(Long registeredClientId);
}
