package com.github.olson1998.synthwave.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.authorizationserver.application.datasource.entity.UserAffiliationData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserAffiliationJpaRepository extends JpaRepository<UserAffiliationData, TSID> {
}
