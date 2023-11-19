package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveUser;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveUserMetadata;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserPropertiesJpaRepository extends JpaRepository<UserData, TSID> {

    @Query("SELECT user FROM UserData user WHERE user.id=:id")
    Optional<UserData> selectUserById(@Param("id") TSID id);

    @Query("""
           SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveUserMetadata(
           user.id,
           affiliation.properties.companyCode,
           affiliation.properties.division
           )
           FROM UserData user
           LEFT OUTER JOIN AffiliationData affiliation
           ON user.id=affiliation.userId
           WHERE user.username=:username
           """)
    Optional<SynthWaveUserMetadata> selectUserMetadataByUsername(@Param("username") String username);

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveUser(
    user.id,
    affiliation.properties.companyCode,
    affiliation.properties.division,
    user.username,
    user.enabled,
    user.expirePeriod,
    password,
    CASE WHEN (COUNT(lock.id) > 0) THEN false ELSE true END
    )
    FROM UserData user
    LEFT OUTER JOIN AffiliationData affiliation
    ON user.id=affiliation.userId
    LEFT OUTER JOIN UserPasswordData password
    ON user.id=password.userId
    LEFT OUTER JOIN UserAccountLockData lock
    ON user.id=lock.userId
    WHERE
    user.username=:username AND password.latestVersion=true
    """)
    Optional<SynthWaveUser> selectSynthWaveUserByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(user.id) >0 THEN true ELSE false END FROM UserData user WHERE user.username=:username")
    boolean selectExistsUserWithUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE UserData user SET user.enabled=:isEnabled WHERE user.id=:id")
    int updateUserEnabledWithGivenUserId(@Param("id") TSID id, @Param("isEnabled") boolean isEnabled);
}
