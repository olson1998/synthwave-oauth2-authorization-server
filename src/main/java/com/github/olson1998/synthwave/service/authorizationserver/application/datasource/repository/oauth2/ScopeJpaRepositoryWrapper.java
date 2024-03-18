package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ScopeBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScopeJpaRepositoryWrapper implements ScopeDataSourceRepository {

    private final ScopeJpaRepository scopeJpaRepository;

    private final ScopeBindingJpaRepository scopeBindingJpaRepository;

    @Override
    public Collection<? extends Scope> getScopesByExamples(Collection<? extends Scope> scopeExamples) {
        var examples = scopeExamples.stream()
                .map(this::createDataExample)
                .toList();
        return scopeJpaRepository.selectScopesByExamples(examples);
    }

    @Override
    public Set<String> getScopesByRegisteredClientId(Long registeredClientId) {
        return scopeJpaRepository.selectScopeNameByRegisteredClientId(registeredClientId);
    }

    @Override
    public void saveAllBounds(Collection<? extends ScopeBinding> scopeCollection) {
        var data = scopeCollection.stream()
                .map(ScopeBindingData::new)
                .toList();
        scopeBindingJpaRepository.saveAll(data);
    }

    private Example<ScopeData> createDataExample(Scope scope) {
        var dataExample = new ScopeData(scope);
        var matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues();
        return Example.of(dataExample, matcher);
    }
}
