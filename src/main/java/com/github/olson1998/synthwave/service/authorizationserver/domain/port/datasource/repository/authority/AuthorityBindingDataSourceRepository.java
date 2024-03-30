package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy.AuthorityBinding;

import java.util.Collection;

public interface AuthorityBindingDataSourceRepository {

    void saveAuthoritiesBounds(Collection<? extends AuthorityBinding> authorityBindingCollection);

    int deleteAuthoritiesBounds(Collection<Long> authoritiesIdCollection);

}
