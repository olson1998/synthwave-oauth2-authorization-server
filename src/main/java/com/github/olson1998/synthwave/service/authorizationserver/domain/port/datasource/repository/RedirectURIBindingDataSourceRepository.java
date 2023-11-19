package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURIBinding;

import java.util.Collection;

public interface RedirectURIBindingDataSourceRepository {

    void saveAll(Collection<RedirectURIBinding> redirectURIBindCollection);
}
