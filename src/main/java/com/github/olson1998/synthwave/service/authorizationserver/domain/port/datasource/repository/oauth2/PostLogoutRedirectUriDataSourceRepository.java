package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.support.web.util.URIModel;

import java.util.Set;

public interface PostLogoutRedirectUriDataSourceRepository {

    Set<URIModel> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId);
}
