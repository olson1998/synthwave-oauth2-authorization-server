package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import java.util.Set;

public interface PostLogoutRedirectUriDataSourceRepository {

    Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId);
}
