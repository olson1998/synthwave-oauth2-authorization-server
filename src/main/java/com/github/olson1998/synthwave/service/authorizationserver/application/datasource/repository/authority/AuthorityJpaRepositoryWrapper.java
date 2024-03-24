package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.Authority;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class AuthorityJpaRepositoryWrapper implements AuthorityDataSourceRepository {

    private final AuthorityJpaRepository authorityJpaRepository;

    @Override
    public Collection<? extends Authority> getAuthoritiesByUserIdAndTimestamp(Long userId, MutableDateTime timestamp) {
        return authorityJpaRepository.selectAuthoritiesByUserIdAndTimestamp(userId, timestamp);
    }

    @Override
    public String[] getActiveAuthoritiesNameByUserId(Long userId) {
        return authorityJpaRepository.selectActiveAuthoritiesNamesByUserId(userId, MutableDateTime.now());
    }

    @Override
    public Collection<? extends Authority> getAuthoritiesByUserId(Long userId) {
        return authorityJpaRepository.selectAuthorityByUserId(userId);
    }

    @Override
    public Collection<Long> getAuthoritiesIdsByExamples(@NonNull Collection<? extends Authority> authorityExamplesCollection) {
        var dataExamples = authorityExamplesCollection.stream()
                .map(AuthorityData::new)
                .map(this::createAuthorityExample)
                .toList();
        return authorityJpaRepository.selectAuthorityIdByExamples(dataExamples);
    }

    @Override
    public Collection<? extends Authority> saveAll(@NonNull Collection<? extends Authority> authoritiesCollection) {
        var data = authoritiesCollection.stream()
                .map(AuthorityData::new)
                .toList();
        return authorityJpaRepository.saveAll(data);
    }

    private Example<AuthorityData> createAuthorityExample(AuthorityData authorityData) {
        return Example.of(authorityData, ExampleMatcher.matching().withIgnoreNullValues());
    }

}
