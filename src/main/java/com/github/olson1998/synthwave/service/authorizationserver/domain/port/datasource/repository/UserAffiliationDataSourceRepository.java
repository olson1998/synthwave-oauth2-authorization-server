package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliation;

public interface UserAffiliationDataSourceRepository {

    void save(UserAffiliation userAffiliation);
}
