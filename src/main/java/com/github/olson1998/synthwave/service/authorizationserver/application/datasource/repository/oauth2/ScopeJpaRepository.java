package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ScopeData;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
interface ScopeJpaRepository extends JpaRepository<ScopeData, Long> {

    @Query(
    """
    SELECT scope.id
    FROM ScopeData scope
    WHERE scope IN :examples
    """
    )
    List<Long> selectScopesIdByExamples(@Param("examples") Collection<Example<ScopeData>> examples);

    @Query(
    """
    SELECT scope.name FROM ScopeData scope
    LEFT OUTER JOIN ScopeBindingData binding
    ON binding.properties.scopeId = scope.id
    WHERE binding.properties.registeredClientId=:registeredClientId
    """
    )
    Set<String> selectScopeNameByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);
}
