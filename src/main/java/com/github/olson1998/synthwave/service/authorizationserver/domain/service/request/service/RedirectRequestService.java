package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectURIRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RedirectRequestService implements RedirectURIRepository {

    private final RedirectDataSourceRepository redirectURIDataSourceRepository;

    @Override
    public void saveAll(List<Redirect> redirectURIses) {
        redirectURIDataSourceRepository.saveAll(redirectURIses);
    }

    @Override
    public void deleteAll(@NonNull List<Redirect> redirectURIses) {

    }
}
