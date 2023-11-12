package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectURIRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RedirectURIRequestService implements RedirectURIRepository {

    private final RedirectURIDataSourceRepository redirectURIDataSourceRepository;

    @Override
    public void saveAll(List<RedirectURI> redirectURIs) {
        redirectURIDataSourceRepository.saveAll(redirectURIs);
    }

    @Override
    public void deleteAll(@NonNull List<RedirectURI> redirectURIs) {

    }
}
