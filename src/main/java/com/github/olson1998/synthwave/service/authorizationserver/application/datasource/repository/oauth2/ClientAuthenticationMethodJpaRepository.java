package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ClientAuthenticationMethodData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface ClientAuthenticationMethodJpaRepository extends JpaRepository<ClientAuthenticationMethodData, Long> {

    @Query(
    """
    SELECT clientAuthenticationMethod
    FROM ClientAuthenticationMethodData clientAuthenticationMethod
    LEFT OUTER JOIN ClientAuthenticationMethodBindingData binding
    ON clientAuthenticationMethod.id=binding.properties.clientAuthenticationMethodId
    WHERE binding.properties.registeredClientId=:registeredClientId
    """
    )
    Set<ClientAuthenticationMethod> selectClientAuthenticationMethodsByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);
}
