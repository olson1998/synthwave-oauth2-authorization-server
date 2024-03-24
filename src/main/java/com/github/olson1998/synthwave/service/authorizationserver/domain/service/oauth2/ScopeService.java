package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ScopeBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ScopeModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public class ScopeService implements ScopeRepository {

    private final ScopeDataSourceRepository scopeDataSourceRepository;

    @Override
    public Set<String> getScopeNameByRegisteredClientId(Long registeredClientId) {
        return scopeDataSourceRepository.getScopesByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends Scope> saveAll(Collection<? extends Scope> scopeCollection) {
        return scopeDataSourceRepository.saveAll(scopeCollection);
    }

    @Override
    public void saveAllBounds(Collection<? extends Scope> scopeCollection, Long registeredClientId) {
        var examples = scopeCollection.stream()
                .map(this::eraseIrrelevantData)
                .toList();
        var scopeIdCollection = scopeDataSourceRepository.getScopesIdByExamples(examples);
        if(examples.size() == scopeIdCollection.size()) {
            var bounds = scopeIdCollection.stream()
                    .map(id -> new ScopeBindingModel(registeredClientId, id))
                    .toList();
            scopeDataSourceRepository.saveAllBounds(bounds);
        } else {
            throw new DataIntegrityViolationException("Requested scopes are not matching");
        }
    }

    private Scope eraseIrrelevantData(Scope scope) {
        ScopeModel scopeModel;
        if(scope instanceof ScopeModel model) {
            scopeModel = model;
        } else {
            scopeModel = new ScopeModel(scope);
        }
        scopeModel.setCreatedOn(null);
        return scopeModel;
    }

}
