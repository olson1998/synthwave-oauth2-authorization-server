package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.RegisteredClientBuilderWrapper;
import org.joda.time.MutableDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RegisteredClientPropertiesJpaRepository extends JpaRepository<RegisteredClientData, Long> {

    @Query(
    """
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.RegisteredClientBuilderWrapper(
    registeredClient.id,
    registeredClient.clientId,
    registeredClient.createdOn,
    registeredClient.name,
    clientSecret.value,
    clientSecret.expireOn,
    clientSettings.requireProofKey,
    clientSettings.requireAuthorizationConsent,
    clientSettings.jwkSetUrl,
    clientSettings.jwsAlgorithm,
    tokenSettings.idTokenSignatureAlgorithm,
    tokenSettings.accessTokenFormat,
    tokenSettings.accessTokenTimeToLive,
    tokenSettings.refreshTokenTimeToLive,
    tokenSettings.reuseRefreshTokens,
    tokenSettings.deviceCodeTimeToLive,
    tokenSettings.authorizationCodeTimeToLive
    )
    FROM RegisteredClientData registeredClient
    LEFT OUTER JOIN RegisteredClientSecretData clientSecret
    ON registeredClient.id=clientSecret.registeredClientId
    LEFT OUTER JOIN ClientSettingsData clientSettings
    ON registeredClient.id=clientSettings.registeredClientId
    LEFT OUTER JOIN TokenSettingsData tokenSettings
    ON registeredClient.id=tokenSettings.registeredClientId
    WHERE registeredClient.id=:registeredClientId
    AND (registeredClient.activeFrom IS NOT NULL AND registeredClient.activeFrom > :timestamp) OR (registeredClient.activeFrom IS NULL)
    """
    )
    Optional<RegisteredClientBuilderWrapper> selectPropertiesByRegisteredClientId(@Param("registeredClientId") Long registeredClientId,
                                                                                  @Param("timestamp") MutableDateTime timestamp);

    @Query(
            """
            SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.RegisteredClientBuilderWrapper(
            registeredClient.id,
            registeredClient.clientId,
            registeredClient.createdOn,
            registeredClient.name,
            clientSecret.value,
            clientSecret.expireOn,
            clientSettings.requireProofKey,
            clientSettings.requireAuthorizationConsent,
            clientSettings.jwkSetUrl,
            clientSettings.jwsAlgorithm,
            tokenSettings.idTokenSignatureAlgorithm,
            tokenSettings.accessTokenFormat,
            tokenSettings.accessTokenTimeToLive,
            tokenSettings.refreshTokenTimeToLive,
            tokenSettings.reuseRefreshTokens,
            tokenSettings.deviceCodeTimeToLive,
            tokenSettings.authorizationCodeTimeToLive
            )
            FROM RegisteredClientData registeredClient
            LEFT OUTER JOIN RegisteredClientSecretData clientSecret
            ON registeredClient.id=clientSecret.registeredClientId
            LEFT OUTER JOIN ClientSettingsData clientSettings
            ON registeredClient.id=clientSettings.registeredClientId
            LEFT OUTER JOIN TokenSettingsData tokenSettings
            ON registeredClient.id=tokenSettings.registeredClientId
            WHERE registeredClient.id=:registeredClientId
            AND (registeredClient.activeFrom IS NOT NULL AND registeredClient.activeFrom > :timestamp) OR (registeredClient.activeFrom IS NULL)
            """
    )
    Optional<RegisteredClientBuilderWrapper> selectPropertiesByClientId(@Param("clientId") String clientId,
                                                                        @Param("timestamp") MutableDateTime selectTimestamp);
}
