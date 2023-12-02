package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.RegisteredClientGenericConfig;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RegisteredClientJpaRepository extends JpaRepository<RegisteredClientData, TSID> {

    @Query("""
           SELECT registeredClient.clientId
           FROM RegisteredClientData registeredClient
           WHERE registeredClient.userId=:userId
           """)
    Optional<String> selectClientIdByUserId(@Param("userId") TSID userId);

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.RegisteredClientGenericConfig(
    registeredClient.id,
    affiliation.properties.companyCode,
    affiliation.properties.division,
    registeredClient.clientId,
    user.username,
    secret,
    tokenSettings.authorizationCodeExpirePeriod,
    tokenSettings.accessTokenExpirePeriod,
    tokenSettings.accessTokenFormat,
    tokenSettings.deviceCodeExpirePeriod,
    tokenSettings.reuseRefreshToken,
    tokenSettings.refreshTokenExpirePeriod,
    tokenSettings.idTokenSignatureAlgorithm,
    settings.requireProofKey,
    settings.requireAuthorizationConsent
    )
    FROM RegisteredClientData registeredClient
    LEFT OUTER JOIN UserData user
    ON registeredClient.userId=user.id
    LEFT OUTER JOIN RegisteredClientSecretData secret
    ON registeredClient.id=secret.registeredClientId
    LEFT OUTER JOIN AffiliationData affiliation
    ON registeredClient.userId=affiliation.userId
    LEFT OUTER JOIN RegisteredClientSettingsData settings
    ON registeredClient.id=settings.registeredClientId
    LEFT OUTER JOIN RegisteredClientTokenSettingsData tokenSettings
    ON registeredClient.id=tokenSettings.registeredClientId
    WHERE registeredClient.id=:registeredClientId
    GROUP BY secret.id
    HAVING MAX(secret.id)=secret.id
    """)
    Optional<RegisteredClientGenericConfig> selectSynthWaveRegisteredClientByRegisteredClientId(@Param("registeredClientId") TSID registeredClientId);

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.RegisteredClientGenericConfig(
    registeredClient.id,
    affiliation.properties.companyCode,
    affiliation.properties.division,
    registeredClient.clientId,
    user.username,
    secret,
    tokenSettings.authorizationCodeExpirePeriod,
    tokenSettings.accessTokenExpirePeriod,
    tokenSettings.accessTokenFormat,
    tokenSettings.deviceCodeExpirePeriod,
    tokenSettings.reuseRefreshToken,
    tokenSettings.refreshTokenExpirePeriod,
    tokenSettings.idTokenSignatureAlgorithm,
    settings.requireProofKey,
    settings.requireAuthorizationConsent
    )
    FROM RegisteredClientData registeredClient
    LEFT OUTER JOIN UserData user
    ON registeredClient.userId=user.id
    LEFT OUTER JOIN AffiliationData affiliation
    ON registeredClient.userId=affiliation.userId
    LEFT OUTER JOIN RegisteredClientSecretData secret
    ON registeredClient.id=secret.registeredClientId
    LEFT OUTER JOIN RegisteredClientSettingsData settings
    ON registeredClient.id=settings.registeredClientId
    LEFT OUTER JOIN RegisteredClientTokenSettingsData tokenSettings
    ON registeredClient.id=tokenSettings.registeredClientId
    WHERE registeredClient.clientId=:username
    GROUP BY secret.id
    HAVING MAX(secret.id)=secret.id
    """)
    Optional<RegisteredClientGenericConfig> selectRegisteredClientConfigByClientId(@Param("username") String username);
}
