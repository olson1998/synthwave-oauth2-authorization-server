package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;

public interface AffiliationRepository {

    void save(AffiliationEntity affiliation);
}
