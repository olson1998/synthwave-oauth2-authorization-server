package com.github.olson1998.synthwave.service.authorizationserver.domain.service.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.AuthorityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.authoritity.UserAuthoritiesModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.repository.AuthorityRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.authority.stereotype.UserAuthorities;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.support.masteritem.annotation.TransactionProcessor;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@TransactionProcessor("AUTHDATA")

@RequiredArgsConstructor
public class AuthorityService implements AuthorityRepository {

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    private final AuthorityBindingDataSourceRepository authorityBindingDataSourceRepository;

    @Override
    public UserAuthorities saveUserAuthorities(UserAuthorities userAuthorities) {
        var userId = userAuthorities.getUserId();
        var authoritiesIds = authorityDataSourceRepository.getAuthoritiesIdsByExamples(userAuthorities.getAuthorities());
        var authoritiesBounds = authoritiesIds.stream()
                .map(authorityId -> new AuthorityBindingModel(userId, authorityId))
                .toList();
        authorityBindingDataSourceRepository.saveAuthoritiesBounds(authoritiesBounds);
        return authorityDataSourceRepository.getAuthoritiesByIds(authoritiesIds).stream()
                .map(AuthorityModel::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), authorityModels -> new UserAuthoritiesModel(userId, authorityModels)));
    }
}
