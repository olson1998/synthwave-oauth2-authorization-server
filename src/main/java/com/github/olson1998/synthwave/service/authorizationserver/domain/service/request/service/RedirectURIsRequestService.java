package com.github.olson1998.synthwave.service.authorizationserver.domain.service.request.service;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.repository.RedirectURIRepository;
import com.github.olson1998.synthwave.support.jpa.exception.UnexpectedNumberOfAffectedRowsException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RedirectURIsRequestService implements RedirectURIRepository {

    private final RedirectURIsDataSourceRepository redirectURIsDataSourceRepository;

    @Override
    public void saveAll(List<RedirectURI> redirectURIs) {
        redirectURIsDataSourceRepository.saveAll(redirectURIs);
    }

    @Override
    public void deleteAll(@NonNull List<RedirectURI> redirectURIs) {
        var expectedAffectedRows = redirectURIs.size();
        var affectedRows = redirectURIsDataSourceRepository.deleteAll(redirectURIs);
        if(affectedRows != expectedAffectedRows){
            throw new UnexpectedNumberOfAffectedRowsException(RedirectURI.class, expectedAffectedRows, affectedRows);
        }
    }
}
