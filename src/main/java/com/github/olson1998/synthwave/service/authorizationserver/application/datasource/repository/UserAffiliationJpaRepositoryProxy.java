package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserAffiliationData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserAffiliationDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAffiliationJpaRepositoryProxy implements UserAffiliationDataSourceRepository {

    private final UserAffiliationJpaRepository userAffiliationJpaRepository;

    @Override
    public void save(UserAffiliationEntity userAffiliationEntity) {
        var data = new UserAffiliationData(userAffiliationEntity);
        userAffiliationJpaRepository.save(data);
    }
}
