package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AuthoritiesBinding;

import java.util.Collection;

public interface AuthoritiesBindingDataSourceRepository {

    void saveAll(Collection<AuthoritiesBinding> authoritiesBindingCollection);
}
