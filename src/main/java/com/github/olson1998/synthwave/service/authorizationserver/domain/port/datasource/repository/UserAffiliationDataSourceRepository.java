package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;

public interface UserAffiliationDataSourceRepository {

    void save(AffiliationEntity affiliationEntity);
}
