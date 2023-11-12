package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AffiliationData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserAffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AffiliationJpaRepositoryProxy implements UserAffiliationDataSourceRepository {

    private final AffiliationJpaRepository affiliationJpaRepository;

    @Override
    public void save(AffiliationEntity affiliationEntity) {
        var data = new AffiliationData(affiliationEntity);
        affiliationJpaRepository.save(data);
    }
}
