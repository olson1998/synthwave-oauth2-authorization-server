package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AuthorizationGrantTypeBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.AuthorizationGrantTypeEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeleteAuthorizationGrantTypeResponseModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeletedRowsModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.AuthorizationGrantTypeDatasourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.AuthorizationGrantTypeEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationGrantTypeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorizationGrantTypeBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorizationGrantTypeResponse;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class AuthorizationGrantTypeService implements AuthorizationGrantTypeRepository {

    private final AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository;

    @Override
    public Collection<? extends AuthorizationGrantTypeEntity> getAllTypes() {
        return authorizationGrantTypeDatasourceRepository.getAllTypes().stream()
                .map(AuthorizationGrantTypeEntityModel::new)
                .toList();
    }

    @Override
    public AuthorizationGrantTypeEntity save(AuthorizationGrantTypeEntity authorizationGrantType) {
        return new AuthorizationGrantTypeEntityModel(authorizationGrantTypeDatasourceRepository.save(authorizationGrantType));
    }

    @Override
    public void saveBounds(Long registeredClientId, Collection<? extends AuthorizationGrantTypeEntity> authorizationGrantTypeCollection) {
        var examples = authorizationGrantTypeCollection.stream()
                .map(this::eraseIrrelevantData)
                .toList();
        var authorizationGrantTypeIds = authorizationGrantTypeDatasourceRepository.getAuthorizationGrantTypeIdByExamples(examples);
        var bounds = authorizationGrantTypeIds.stream()
                .map(authorizationGrantTypeId -> new AuthorizationGrantTypeBindingModel(registeredClientId, authorizationGrantTypeId))
                .toList();
        authorizationGrantTypeDatasourceRepository.saveBounds(bounds);
    }

    @Override
    public DeleteAuthorizationGrantTypeResponse delete(Long authorizationGrantTypeId) {
        var deletedBoundsQty = authorizationGrantTypeDatasourceRepository.deleteBoundsByAuthorizationGrantTypeId(authorizationGrantTypeId);
        return new DeleteAuthorizationGrantTypeResponseModel(authorizationGrantTypeId, new DeletedRowsModel(deletedBoundsQty));
    }

    @Override
    public DeleteAuthorizationGrantTypeBindingResponse deleteBinding(Long registeredClientId, Long authorizationGrantTypeId) {
        var deletedBoundsQty = authorizationGrantTypeDatasourceRepository.deleteBoundsByAuthorizationGrantTypeIdAndRegisteredClientId(
                registeredClientId,
                authorizationGrantTypeId
        );

        return null;
    }

    private AuthorizationGrantTypeEntityModel eraseIrrelevantData(AuthorizationGrantTypeEntity authorizationGrantType) {
        AuthorizationGrantTypeEntityModel model;
        if(authorizationGrantType instanceof AuthorizationGrantTypeEntityModel authorizationGrantTypeEntityModel) {
            model = authorizationGrantTypeEntityModel;
        } else {
            model = new AuthorizationGrantTypeEntityModel(authorizationGrantType);
        }
        model.setCreatedOn(null);
        return model;
    }
}
