package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ScopeBinding;
import com.github.olson1998.synthwave.support.jpa.spec.JpaSpecificationUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScopeJpaRepositoryWrapper implements ScopeDataSourceRepository {

    private final ScopeJpaRepository scopeJpaRepository;

    private final ScopeBindingJpaRepository scopeBindingJpaRepository;

    @Override
    public Collection<? extends Scope> getAllScopes() {
        return scopeJpaRepository.findAll();
    }

    @Override
    public Collection<Long> getScopeIdById(Collection<Long> scopeIdCollection) {
        return scopeJpaRepository.selectScopeIdById(scopeIdCollection);
    }

    @Override
    public Collection<? extends Scope> getScopeByNames(Collection<String> scopeNames) {
        return scopeJpaRepository.selectScopeByNames(scopeNames);
    }

    @Override
    public Collection<? extends Scope> getScopesByExamples(@NonNull Collection<? extends Scope> scopeExamples) {
        var dataSpecChain = createScopeDataExampleSpecChain(scopeExamples);
        return scopeJpaRepository.findAll(dataSpecChain);
    }

    @Override
    public Set<String> getScopesByRegisteredClientId(Long registeredClientId) {
        return scopeJpaRepository.selectScopeNameByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends Scope> saveAll(Collection<? extends Scope> scopeCollection) {
        var data = scopeCollection.stream()
                .map(ScopeData::new)
                .toList();
        return scopeJpaRepository.saveAll(data);
    }

    @Override
    public void saveAllBounds(Collection<? extends ScopeBinding> scopeCollection) {
        var data = scopeCollection.stream()
                .map(ScopeBindingData::new)
                .toList();
        scopeBindingJpaRepository.saveAll(data);
    }

    @Override
    public int deleteScopeBoundsByScopeId(Collection<Long> scopeIdCollection) {
        return scopeBindingJpaRepository.deleteScopeBindingByScopeId(scopeIdCollection);
    }

    private Specification<ScopeData> createScopeDataExampleSpecChain(Collection<? extends Scope> scopeExamplesCollection) {
        return scopeExamplesCollection.stream()
                .map(ScopeData::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), JpaSpecificationUtil::createDataExamplesSpecChain));
    }

}
