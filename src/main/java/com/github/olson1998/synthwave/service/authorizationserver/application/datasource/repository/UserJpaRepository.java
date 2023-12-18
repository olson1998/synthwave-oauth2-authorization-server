package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.OidcUserInfoPropertiesData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.UserPropertiesData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.UserAffiliationData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserJpaRepository extends JpaRepository<UserData, TSID> {

    @Query("SELECT user FROM UserData user WHERE user.id=:id")
    Optional<UserData> selectUserById(@Param("id") TSID id);

    @Query("""
           SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.UserMetadataData(
           user.id,
           user.username,
           affiliation.properties.companyCode,
           affiliation.properties.division
           )
           FROM UserData user
           LEFT OUTER JOIN AffiliationData affiliation
           ON user.id=affiliation.userId
           WHERE user.username=:username
           """)
    Optional<UserAffiliationData> selectUserMetadataByUsername(@Param("username") String username);

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.UserPropertiesData(
    user.id,
    affiliation.properties.companyCode,
    affiliation.properties.division,
    user.username,
    user.enabled,
    user.expireDateTime,
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
    WHERE user.username=:username
    GROUP BY password.id
    HAVING MAX(password.id)=password.id
    """)
    Optional<UserPropertiesData> selectSynthWaveUserByUsername(@Param("username") String username);

    @Query("""
           SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.OidcUserInfoPropertiesData(
           user.id,
           user.username,
           user.expireDateTime,
           email.emailAddress,
           email.verified,
           phone.phoneNumber,
           phone.verified,
           info.name,
           info.middleName,
           info.givenName,
           info.userGender,
           info.userZoneId
           )
           FROM UserData user
           LEFT OUTER JOIN UserInfoData info
           ON user.id=info.userId
           LEFT OUTER JOIN UserPhoneData phone
           ON user.id=phone.userId
           LEFT OUTER JOIN UserEmailData email
           ON user.id=email.userId
           WHERE user.username=:username
           """)
    Optional<OidcUserInfoPropertiesData> selectOidcUserInfoByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(user.id) >0 THEN true ELSE false END FROM UserData user WHERE user.username=:username")
    boolean selectExistsUserWithUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE UserData user SET user.enabled=:isEnabled WHERE user.id=:id")
    int updateUserEnabledWithGivenUserId(@Param("id") TSID id, @Param("isEnabled") boolean isEnabled);
}
