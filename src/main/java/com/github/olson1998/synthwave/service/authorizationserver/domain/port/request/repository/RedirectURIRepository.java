package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RedirectURIRepository {

    @Transactional
    void saveAll(List<Redirect> redirectURIses);

    @Transactional
    void deleteAll(List<Redirect> redirectURIses);
}
