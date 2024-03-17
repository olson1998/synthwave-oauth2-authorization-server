package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import java.util.Set;

public interface ScopeDataSourceRepository {

    Set<String> getScopesByRegisteredClientId(Long registeredClientId);
}
