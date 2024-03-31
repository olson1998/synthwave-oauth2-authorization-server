package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface  ScopeRepository {

    Collection<? extends Scope> getAllScopes();

    Set<String> getScopeNameByRegisteredClientId(Long registeredClientId);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends Scope> saveAll(Collection<? extends Scope> scopeCollection);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends Scope> saveAllModels(Collection<? extends Scope> scopeCollection);

    @Transactional(rollbackFor = Exception.class)
    void saveAllBounds(Collection<? extends Scope> scopeCollection, Long registeredClientId);

    @Transactional(rollbackFor = Exception.class)
    DeleteScopeResponse deleteScopes(Collection<Long> scopeIdCollection);

    @Transactional(rollbackFor = Exception.class)
    DeleteScopeBindingResponse deleteScopeBounds(Collection<? extends Scope> scopeCollection, Long registeredClientId);

}
