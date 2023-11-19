package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AffiliationData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.AffiliationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AffiliationJpaRepositoryProxy implements AffiliationDataSourceRepository {

    private final AffiliationJpaRepository affiliationJpaRepository;

    @Override
    public void save(AffiliationEntity affiliationEntity) {
        var data = new AffiliationData(affiliationEntity);
        affiliationJpaRepository.save(data);
    }
}
