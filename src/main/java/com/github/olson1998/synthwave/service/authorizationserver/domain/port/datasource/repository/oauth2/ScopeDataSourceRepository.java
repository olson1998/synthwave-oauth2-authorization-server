package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ScopeBinding;

import java.util.Collection;
import java.util.Set;

public interface ScopeDataSourceRepository {

    Collection<Long> getScopesIdByExamples(Collection<? extends Scope> scopeExamples);

    Set<String> getScopesByRegisteredClientId(Long registeredClientId);

    Collection<? extends Scope> saveAll(Collection<? extends Scope> scopeCollection);

    void saveAllBounds(Collection<? extends ScopeBinding> scopeCollection);
}
