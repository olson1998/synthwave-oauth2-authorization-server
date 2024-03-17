package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScopeJpaRepositoryWrapper implements ScopeDataSourceRepository {

    private final ScopeJpaRepository scopeJpaRepository;

    @Override
    public Collection<Scope> getScopesByExamples(Collection<? extends Scope> scopeExamples) {
        return null;
    }

    @Override
    public Set<String> getScopesByRegisteredClientId(Long registeredClientId) {
        return scopeJpaRepository.selectScopeNameByRegisteredClientId(registeredClientId);
    }
}
