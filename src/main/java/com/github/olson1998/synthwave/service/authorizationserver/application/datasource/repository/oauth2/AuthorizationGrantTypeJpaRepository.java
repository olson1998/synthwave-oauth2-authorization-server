package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface AuthorizationGrantTypeJpaRepository extends JpaRepository<AuthorizationGrantTypeData, Long> {

    @Query(
    """
    SELECT authorizationGrantType
    FROM AuthorizationGrantTypeData authorizationGrantType
    LEFT OUTER JOIN AuthorizationGrantTypeBindingData binding
    ON authorizationGrantType.id = binding.properties.authorizationGrantTypeId
    WHERE binding.properties.registeredClientId=:registeredClientId
    """
    )
    Set<AuthorizationGrantType> selectAuthorizationGrantTypeByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);
}
