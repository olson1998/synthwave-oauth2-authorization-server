package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserPasswordData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserPasswordJpaRepository extends JpaRepository<UserPasswordData, TSID> {

    @Query("""
           SELECT password
           FROM UserPasswordData password
           LEFT OUTER JOIN RegisteredClientData registeredClient
           ON registeredClient.userId=password.userId
           WHERE registeredClient.clientId=:clientId
           """)
    Optional<UserPasswordData> selectPasswordByClientId(@Param("clientId") String clientId);

}
