package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.SynthWaveRegisteredClientData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SynthWaveRegisteredClientJpaRepository extends JpaRepository<SynthWaveRegisteredClientData, TSID> {
}
