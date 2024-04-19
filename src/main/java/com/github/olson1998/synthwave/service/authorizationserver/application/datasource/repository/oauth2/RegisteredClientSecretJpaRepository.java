package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientSecretData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface RegisteredClientSecretJpaRepository extends JpaRepository<RegisteredClientSecretData, Long> {

    @Query("""
           SELECT CASE WHEN COUNT(client.id) >0 THEN true ELSE false END
           FROM RegisteredClientData client
           WHERE client.id=:registeredClientId
           """)
    boolean selectCaseWhenRegisteredClientExists(@Param("registeredClientId") Long registeredClientId);

}
