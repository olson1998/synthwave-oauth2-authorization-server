package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.ScopeBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
interface ScopeBindingJpaRepository extends JpaRepository<ScopeBindingData, ScopeBindingValue> {

    @Query("""
           DELETE FROM ScopeBindingData binding
           WHERE binding.properties.scopeId=:scopeIdCollection
           """)
    int deleteScopeBindingByScopeId(@Param("")Collection<Long> scopeIdCollection);

}
