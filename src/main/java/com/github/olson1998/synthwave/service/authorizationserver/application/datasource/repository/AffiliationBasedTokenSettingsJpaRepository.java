package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AffiliationBasedTokenSettingsData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AffiliationProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AffiliationBasedTokenSettingsJpaRepository extends JpaRepository<AffiliationBasedTokenSettingsData, AffiliationProperties> {
}
