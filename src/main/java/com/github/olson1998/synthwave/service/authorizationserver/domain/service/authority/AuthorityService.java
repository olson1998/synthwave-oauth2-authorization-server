package com.github.olson1998.synthwave.service.authorizationserver.domain.service.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.UserAuthoritiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionProcessor;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;

@TransactionProcessor("AUTHDATA")

@RequiredArgsConstructor
public class AuthorityService implements AuthorityRepository {

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    private final AuthorityBindingDataSourceRepository authorityBindingDataSourceRepository;

    @Override
    public String[] getActiveAuthoritiesNamesByUserId(Long userId) {
        return authorityDataSourceRepository.getActiveAuthoritiesNameByUserId(userId);
    }

    @Override
    public UserAuthorities executeSaveUserAuthoritiesTransaction(UserAuthorities userAuthorities) {
        saveUserAuthorities(userAuthorities);
        var authoritiesModels = authorityDataSourceRepository.getAuthoritiesByUserId(userAuthorities.getUserId())
                .stream()
                .map(AuthorityModel::new)
                .toList();
        return new UserAuthoritiesModel(userAuthorities.getUserId(), authoritiesModels);
    }

    @Override
    public void saveUserAuthorities(UserAuthorities userAuthorities) {
        var userId = userAuthorities.getUserId();
        var examples = userAuthorities.getAuthorities()
                .stream()
                .peek(this::eraseIrrelevantData)
                .toList();
        var authoritiesIds = authorityDataSourceRepository.getAuthoritiesIdsByExamples(examples);
        var authoritiesBounds = authoritiesIds.stream()
                .map(authorityId -> new AuthorityBindingModel(userId, authorityId, MutableDateTime.now()))
                .toList();
        authorityBindingDataSourceRepository.saveAuthoritiesBounds(authoritiesBounds);
    }

    private void eraseIrrelevantData(Authority authority) {
        AuthorityModel authorityModel;
        if(authority instanceof AuthorityModel model) {
            authorityModel = model;
        } else {
            authorityModel = new AuthorityModel(authority);
        }
        authorityModel.setActiveFrom(null);
        authorityModel.setCreatedOn(null);
        authorityModel.setActiveFrom(null);
    }

}
