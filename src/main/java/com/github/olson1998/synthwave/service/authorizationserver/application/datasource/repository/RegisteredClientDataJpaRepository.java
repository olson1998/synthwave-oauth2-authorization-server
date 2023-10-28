package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveRegisteredClientPropertiesImpl;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RegisteredClientDataJpaRepository extends JpaRepository<RegisteredClientData, TSID> {

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveRegisteredClientPropertiesImpl(
    registeredClient.id,
    affiliation.values.companyCode,
    affiliation.values.division,
    registeredClient.clientId,
    user.username,
    password.id,
    password.value,
    password.expirePeriod,
    registeredClient.type,
    tokenSettings.authorizationCodeExpirePeriod,
    tokenSettings.accessTokenExpirePeriod,
    tokenSettings.accessTokenFormat,
    tokenSettings.deviceCodeExpirePeriod,
    tokenSettings.reuseRefreshToken,
    tokenSettings.refreshTokenExpirePeriod,
    tokenSettings.idTokenSignatureAlgorithm
    )
    FROM RegisteredClientData registeredClient
    LEFT OUTER JOIN SynthWaveUserData user
    ON registeredClient.userId=user.id
    LEFT OUTER JOIN UserPasswordData password
    ON registeredClient.userId=password.userId
    LEFT OUTER JOIN UserAffiliationData affiliation
    ON registeredClient.userId=affiliation.userId
    LEFT OUTER JOIN AffiliationBasedTokenSettingsData tokenSettings
    ON affiliation.values.companyCode=tokenSettings.affiliation.companyCode
    AND affiliation.values.division=tokenSettings.affiliation.division
    WHERE registeredClient.id=:registeredClientId
    AND password.latestVersion=true
    """)
    Optional<SynthWaveRegisteredClientPropertiesImpl> selectSynthWaveRegisteredClientByRegisteredClientId(TSID registeredClientId);

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveRegisteredClientPropertiesImpl(
    registeredClient.id,
    affiliation.values.companyCode,
    affiliation.values.division,
    registeredClient.clientId,
    user.username,
    password.id,
    password.value,
    password.expirePeriod,
    registeredClient.type,
    tokenSettings.authorizationCodeExpirePeriod,
    tokenSettings.accessTokenExpirePeriod,
    tokenSettings.accessTokenFormat,
    tokenSettings.deviceCodeExpirePeriod,
    tokenSettings.reuseRefreshToken,
    tokenSettings.refreshTokenExpirePeriod,
    tokenSettings.idTokenSignatureAlgorithm
    )
    FROM RegisteredClientData registeredClient
    LEFT OUTER JOIN SynthWaveUserData user
    ON registeredClient.userId=user.id
    LEFT OUTER JOIN UserPasswordData password
    ON registeredClient.userId=password.userId
    LEFT OUTER JOIN UserAffiliationData affiliation
    ON registeredClient.userId=affiliation.userId
    LEFT OUTER JOIN AffiliationBasedTokenSettingsData tokenSettings
    ON affiliation.values.companyCode=tokenSettings.affiliation.companyCode
    AND affiliation.values.division=tokenSettings.affiliation.division
    WHERE registeredClient.clientId=:clientId
    AND password.latestVersion=true
    """)
    Optional<SynthWaveRegisteredClientPropertiesImpl> selectSynthWaveRegisteredClientByClientId(String clientId);
}