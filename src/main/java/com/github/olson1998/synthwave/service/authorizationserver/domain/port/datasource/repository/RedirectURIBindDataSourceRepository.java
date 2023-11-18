package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURIBind;

import java.util.Collection;

public interface RedirectURIBindDataSourceRepository {

    void saveAll(Collection<RedirectURIBind> redirectURIBindCollection);
}
