package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority.AuthorityBindingDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class AuthorityBindingJpaRepositoryWrapper implements AuthorityBindingDataSourceRepository {

    private final AuthorityBindingJpaRepository authorityBindingJpaRepository;

    @Override
    public void saveAuthoritiesBounds(Collection<? extends AuthorityBinding> authorityBindingCollection) {
        var data = authorityBindingCollection.stream()
                .map(AuthorityBindingData::new)
                .toList();
        authorityBindingJpaRepository.saveAll(data);
    }
}
