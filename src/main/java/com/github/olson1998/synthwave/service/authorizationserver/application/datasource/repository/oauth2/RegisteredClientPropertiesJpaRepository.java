package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RegisteredClientPropertiesData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.RegisteredClientPropertiesQuerySearchResultImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RegisteredClientPropertiesJpaRepository extends JpaRepository<RegisteredClientPropertiesData, Long> {

    @Query(
    """
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.RegisteredClientPropertiesQuerySearchResultImpl(
    registeredClient.id,
    registeredClient.clientId,
    registeredClient.createdOn,
    registeredClient.name,
    secret.value,
    secret.expireOn,
    clientSettings.requireProofKey,
    clientSettings.requireAuthorizationConsent,
    clientSettings.jwkSetUrl,
    clientSettings.jwsAlgorithm,
    tokenSettings.idTokenSignatureAlgorithm,
    tokenSettings.oAuth2TokenFormat,
    tokenSettings.accessTokenTimeToLive,
    tokenSettings.refreshTokenTimeToLive,
    tokenSettings.reuseRefreshTokens,
    tokenSettings.deviceCodeTimeToLive,
    tokenSettings.authorizationCodeTimeToLive
    )
    FROM RegisteredClientPropertiesData registeredClient
    LEFT OUTER JOIN RegisteredClientSecretData secret
    ON registeredClient.id=secret.registeredClientId
    LEFT OUTER JOIN ClientSettingsData clientSettings
    ON registeredClient.id=clientSettings.registeredClientId
    LEFT OUTER JOIN TokenSettingsData tokenSettings
    ON registeredClient.id=tokenSettings.registeredClientId
    WHERE registeredClient.id=:registeredClientId
    """
    )
    Optional<RegisteredClientPropertiesQuerySearchResultImpl> selectPropertiesByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);

    @Query(
            """
            SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.query.RegisteredClientPropertiesQuerySearchResultImpl(
            registeredClient.id,
            registeredClient.clientId,
            registeredClient.createdOn,
            registeredClient.name,
            secret.value,
            secret.expireOn,
            clientSettings.requireProofKey,
            clientSettings.requireAuthorizationConsent,
            clientSettings.jwkSetUrl,
            clientSettings.jwsAlgorithm,
            tokenSettings.idTokenSignatureAlgorithm,
            tokenSettings.oAuth2TokenFormat,
            tokenSettings.accessTokenTimeToLive,
            tokenSettings.refreshTokenTimeToLive,
            tokenSettings.reuseRefreshTokens,
            tokenSettings.deviceCodeTimeToLive,
            tokenSettings.authorizationCodeTimeToLive
            )
            FROM RegisteredClientPropertiesData registeredClient
            LEFT OUTER JOIN RegisteredClientSecretData secret
            ON registeredClient.id=secret.registeredClientId
            LEFT OUTER JOIN ClientSettingsData clientSettings
            ON registeredClient.id=clientSettings.registeredClientId
            LEFT OUTER JOIN TokenSettingsData tokenSettings
            ON registeredClient.id=tokenSettings.registeredClientId
            WHERE registeredClient.clientId=:clientId
            """
    )
    Optional<RegisteredClientPropertiesQuerySearchResultImpl> selectPropertiesByClientId(@Param("clientId") String clientId);
}
