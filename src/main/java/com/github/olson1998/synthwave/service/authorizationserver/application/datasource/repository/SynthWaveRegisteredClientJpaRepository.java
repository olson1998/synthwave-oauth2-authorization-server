package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.SynthWaveRegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveRegisteredClientPropertiesImpl;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SynthWaveRegisteredClientJpaRepository extends JpaRepository<SynthWaveRegisteredClientData, TSID> {

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveRegisteredClientPropertiesImpl(
    registeredClient.id,
    registeredClient.clientId,
    user.username,
    password.id,
    password.value,
    password.expirePeriod
    )
    FROM SynthWaveRegisteredClientData registeredClient
    LEFT OUTER JOIN SynthWaveUserData user
    ON registeredClient.userId=user.id
    LEFT OUTER JOIN UserPasswordData password
    ON registeredClient.userId=password.userId
    WHERE registeredClient.id=:registeredClientId
    AND password.latestVersion=true
    """)
    Optional<SynthWaveRegisteredClientPropertiesImpl> selectSynthWaveRegisteredClientByRegisteredClientId(TSID registeredClientId);

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveRegisteredClientPropertiesImpl(
    registeredClient.id,
    registeredClient.clientId,
    user.username,
    password.id,
    password.value,
    password.expirePeriod
    )
    FROM SynthWaveRegisteredClientData registeredClient
    LEFT OUTER JOIN SynthWaveUserData user
    ON registeredClient.userId=user.id
    LEFT OUTER JOIN UserPasswordData password
    ON registeredClient.userId=password.userId
    WHERE registeredClient.clientId=:clientId
    AND password.latestVersion=true
    """)
    Optional<SynthWaveRegisteredClientPropertiesImpl> selectSynthWaveRegisteredClientByClientId(String clientId);
}
