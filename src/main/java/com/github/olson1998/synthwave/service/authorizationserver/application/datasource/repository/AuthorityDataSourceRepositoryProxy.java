package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AuthorityData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthorityDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthorityDataSourceRepositoryProxy implements AuthorityDataSourceRepository {

    private final AuthorityJpaRepository authorityJpaRepository;

    @Override
    public Collection<SimpleGrantedAuthority> getSimpleGrantedAuthoritiesAndAllChildAuthoritiesByParentValue(String authority) {
        Objects.requireNonNull(authority, "Cannot find children authorities if parent authority is null");
        return authorityJpaRepository.selectSimpleGrantedAuthoritiesByParentAuthority(authority);
    }

    @Override
    public void saveAll(Collection<Authority> authorityCollection) {
        Objects.requireNonNull(authorityCollection, "Cannot save empty authority collection");
        var authorityData = authorityCollection.stream()
                .map(AuthorityData::new)
                .toList();
        authorityJpaRepository.saveAll(authorityData);
    }
}
