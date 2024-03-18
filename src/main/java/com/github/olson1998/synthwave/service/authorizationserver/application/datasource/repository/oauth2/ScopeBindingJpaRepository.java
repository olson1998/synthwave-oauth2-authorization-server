package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.ScopeBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ScopeBindingJpaRepository extends JpaRepository<ScopeBindingData, ScopeBindingValue> {
}
