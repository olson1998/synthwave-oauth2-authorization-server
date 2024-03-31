package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ScopeBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ScopeModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeleteScopeResponseModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeletedBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ScopeDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.Scope;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteScopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public class ScopeService implements ScopeRepository {

    private final ScopeDataSourceRepository scopeDataSourceRepository;

    @Override
    public Collection<? extends Scope> getAllScopes() {
        return scopeDataSourceRepository.getAllScopes()
                .stream()
                .map(ScopeModel::new)
                .toList();
    }

    @Override
    public Set<String> getScopeNameByRegisteredClientId(Long registeredClientId) {
        return scopeDataSourceRepository.getScopesByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends Scope> saveAll(Collection<? extends Scope> scopeCollection) {
        return scopeDataSourceRepository.saveAll(scopeCollection);
    }

    @Override
    public Collection<? extends Scope> saveAllModels(Collection<? extends Scope> scopeCollection) {
        return saveAll(scopeCollection).stream()
                .map(ScopeModel::new)
                .toList();
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

    @Override
    public DeleteScopeResponse deleteScopes(Collection<Long> scopeIdCollection) {
        var idCollection = scopeDataSourceRepository.getScopeIdById(scopeIdCollection);
        var deletedBoundsQty = scopeDataSourceRepository.deleteScopeBoundsByScopeId(idCollection);
        return new DeleteScopeResponseModel(idCollection, new DeletedBindingModel(deletedBoundsQty));
    }

    @Override
    public DeleteScopeBindingResponse deleteScopeBounds(Collection<? extends Scope> scopeCollection, Long registeredClientId) {
        var idCollection = scopeDataSourceRepository.getScopesIdByExamples(scopeCollection);

        return null;
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
