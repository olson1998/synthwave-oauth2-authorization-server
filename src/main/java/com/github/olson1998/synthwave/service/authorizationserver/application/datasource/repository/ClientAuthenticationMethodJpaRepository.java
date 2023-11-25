package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.ClientAuthenticationMethodBoundData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.ClientAuthenticationMethodBoundProperties;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ClientAuthenticationMethodJpaRepository extends JpaRepository<ClientAuthenticationMethodBoundData, ClientAuthenticationMethodBoundProperties> {

    @Query("""
           SELECT clientAuthenticationMethod.binding.clientAuthenticationMethod
           FROM ClientAuthenticationMethodBoundData clientAuthenticationMethod
           WHERE clientAuthenticationMethod.binding.registeredClientId=:registeredClientId
           """)
    List<ClientAuthenticationMethod> selectClientAuthenticationMethodByRegisteredClientId(@Param("registeredClientId")TSID registeredClientId);
}
