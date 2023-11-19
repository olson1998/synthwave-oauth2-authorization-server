package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AffiliationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AffiliationService implements AffiliationRepository {

    private final AffiliationDataSourceRepository affiliationDataSourceRepository;

    @Override
    public void save(AffiliationEntity affiliation) {
        affiliationDataSourceRepository.save(affiliation);
    }
}
