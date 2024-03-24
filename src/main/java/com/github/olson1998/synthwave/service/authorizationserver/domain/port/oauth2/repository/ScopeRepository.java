package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface  ScopeRepository {

    Set<String> getScopeNameByRegisteredClientId(Long registeredClientId);

    @Transactional(rollbackFor = Exception.class)
    Collection<? extends Scope> saveAll(Collection<? extends Scope> scopeCollection);

    void saveAllBounds(Collection<? extends Scope> scopeCollection, Long registeredClientId);

}
