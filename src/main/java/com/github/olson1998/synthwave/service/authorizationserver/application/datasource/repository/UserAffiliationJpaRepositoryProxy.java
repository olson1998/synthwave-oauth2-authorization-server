package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.UserAffiliationDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAffiliationJpaRepositoryProxy implements UserAffiliationDataSourceRepository {

    private final UserAffiliationJpaRepository userAffiliationJpaRepository;
}
