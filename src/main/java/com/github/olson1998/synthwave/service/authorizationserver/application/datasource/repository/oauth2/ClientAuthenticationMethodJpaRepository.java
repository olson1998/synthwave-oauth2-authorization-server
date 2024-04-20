package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ClientAuthenticationMethodData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
interface ClientAuthenticationMethodJpaRepository extends JpaRepository<ClientAuthenticationMethodData, Long>, JpaSpecificationExecutor<ClientAuthenticationMethodData> {

    @Query("""
           SELECT method
           FROM ClientAuthenticationMethodData method
           WHERE method.method IN :methods
           """)
    List<ClientAuthenticationMethodData> selectClientAuthenticationMethodByMethods(@Param("methods") Collection<ClientAuthenticationMethod> clientAuthenticationMethods);

    @Query(
    """
    SELECT clientAuthenticationMethod.method
    FROM ClientAuthenticationMethodData clientAuthenticationMethod
    LEFT OUTER JOIN ClientAuthenticationMethodBindingData binding
    ON clientAuthenticationMethod.id=binding.properties.clientAuthenticationMethodId
    WHERE binding.properties.registeredClientId=:registeredClientId
    """
    )
    Set<ClientAuthenticationMethod> selectClientAuthenticationMethodsByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);
}
