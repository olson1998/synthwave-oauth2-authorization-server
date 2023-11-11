package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RedirectURIRepository {

    @Transactional
    void saveAll(List<RedirectURI> redirectURIs);

    @Transactional
    void deleteAll(List<RedirectURI> redirectURIs);
}
