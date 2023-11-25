package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectBound;

import java.util.Collection;

public interface RedirectBoundDataSourceRepository {

    void saveAll(Collection<RedirectBound> redirectURIBindCollection);
}
