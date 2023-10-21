package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AuthoritiesBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthoritiesBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthoritiesBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthoritiesBindingDataSourceRepositoryProxy implements AuthoritiesBindingDataSourceRepository {

    private final AuthoritiesBindingJpaRepository authorityBindingJpaRepository;

    @Override
    public void saveAll(Collection<AuthoritiesBinding> authoritiesBindingCollection) {
        Objects.requireNonNull(authoritiesBindingCollection, "Cannot persist empty authorities bindings collection");
        var authoritiesBindingsData = authoritiesBindingCollection.stream()
                .map(AuthoritiesBindingData::new)
                .toList();
        authorityBindingJpaRepository.saveAll(authoritiesBindingsData);
    }
}
