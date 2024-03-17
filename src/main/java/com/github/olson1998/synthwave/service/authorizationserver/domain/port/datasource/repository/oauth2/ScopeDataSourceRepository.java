package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;

import java.util.Collection;
import java.util.Set;

public interface ScopeDataSourceRepository {

    Collection<Scope> getScopesByExamples(Collection<? extends Scope> scopeExamples);

    Set<String> getScopesByRegisteredClientId(Long registeredClientId);
}
