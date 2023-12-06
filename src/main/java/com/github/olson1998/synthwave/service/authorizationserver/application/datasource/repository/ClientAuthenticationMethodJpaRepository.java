package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.ClientAuthenticationMethodBoundData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.ClientAuthenticationMethodBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
interface ClientAuthenticationMethodJpaRepository extends JpaRepository<ClientAuthenticationMethodBoundData, ClientAuthenticationMethodBinding> {

    @Query("""
           SELECT DISTINCT clientAuthenticationMethod.binding.clientAuthenticationMethod
           FROM ClientAuthenticationMethodBoundData clientAuthenticationMethod
           WHERE clientAuthenticationMethod.binding.registeredClientId=:registeredClientId
           """)
    Set<ClientAuthenticationMethod> selectClientAuthenticationMethodByRegisteredClientId(@Param("registeredClientId")TSID registeredClientId);
}
