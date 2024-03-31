package com.github.olson1998.synthwave.service.authorizationserver.domain.service.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeleteAuthorityResponseModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeleteUserAuthorityBindingResponseModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.rest.DeletedBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteAuthorityResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteUserAuthorityBindingResponse;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;

import java.util.Collection;

@RequiredArgsConstructor
public class AuthorityService implements AuthorityRepository {

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    private final AuthorityBindingDataSourceRepository authorityBindingDataSourceRepository;

    @Override
    public Collection<? extends Authority> getAuthoritiesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp) {
        return authorityDataSourceRepository.getAuthoritiesByUserIdAndTimestamp(userId, timestamp);
    }

    @Override
    public String[] getActiveAuthoritiesNamesByUserId(Long userId) {
        return authorityDataSourceRepository.getActiveAuthoritiesNameByUserId(userId);
    }

    @Override
    public Collection<? extends Authority> saveAll(Collection<Authority> authorityCollection) {
        return authorityDataSourceRepository.saveAll(authorityCollection).stream()
                .map(AuthorityModel::new)
                .toList();
    }

    @Override
    public void saveUserAuthorities(UserAuthorities userAuthorities) {
        var userId = userAuthorities.getUserId();
        var examples = userAuthorities.getAuthorities()
                .stream()
                .map(this::eraseIrrelevantData)
                .toList();
        var authoritiesIds = authorityDataSourceRepository.getAuthoritiesIdsByExamples(examples);
        var authoritiesBounds = authoritiesIds.stream()
                .map(authorityId -> new AuthorityBindingModel(userId, authorityId, MutableDateTime.now()))
                .toList();
        authorityBindingDataSourceRepository.saveAuthoritiesBounds(authoritiesBounds);
    }

    @Override
    public DeleteAuthorityResponse deleteAuthorities(Collection<Long> idCollection) {
        var authoritiesIdCollection = authorityDataSourceRepository.getIdByAuthorityIdCollection(idCollection);
        var deletedBounds = authorityBindingDataSourceRepository.deleteAuthoritiesBounds(authoritiesIdCollection);
        return new DeleteAuthorityResponseModel<>(authoritiesIdCollection, new DeletedBindingModel(deletedBounds));
    }

    @Override
    public DeleteUserAuthorityBindingResponse deleteUserAuthoritiesBounds(Long userId, Collection<Long> idCollection) {
        var authoritiesIdCollection = authorityDataSourceRepository.getIdByAuthorityIdCollection(idCollection);
        var deleteBounds = authorityBindingDataSourceRepository.deleteUserAuthorityBounds(userId, idCollection);
        return new DeleteUserAuthorityBindingResponseModel(userId, authoritiesIdCollection);
    }

    private Authority eraseIrrelevantData(Authority authority) {
        AuthorityModel authorityModel;
        if(authority instanceof AuthorityModel model) {
            authorityModel = model;
        } else {
            authorityModel = new AuthorityModel(authority);
        }
        authorityModel.setActiveFrom(null);
        authorityModel.setCreatedOn(null);
        authorityModel.setActiveFrom(null);
        return authorityModel;
    }

}
