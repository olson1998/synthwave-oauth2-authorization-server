package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectClientBound;

import java.util.Collection;

public interface RedirectClientBoundDataSourceRepository {

    void saveAll(Collection<RedirectClientBound> redirectClientBoundCollection);
}
