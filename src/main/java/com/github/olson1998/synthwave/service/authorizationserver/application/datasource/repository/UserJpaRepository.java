package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserData;
import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveUser;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserJpaRepository extends JpaRepository<UserData, TSID> {

    @Query("""
    SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model.SynthWaveUser(
    user.id,
    affiliation.companyCode,
    affiliation.division,
    affiliation.role,
    user.username,
    user.enabled,
    user.expirePeriod,
    password,
    CASE WHEN (COUNT(ban.id) > 0) THEN true ELSE false END
    )
    FROM UserData user
    LEFT OUTER JOIN UserAffiliationData affiliation
    ON user.id=affiliation.userId
    LEFT OUTER JOIN UserPasswordData password
    ON user.id=password.userId
    LEFT OUTER JOIN UserBanData ban
    ON user.id=ban.userId
    WHERE
    user.username=:username AND password.latestVersion=true
    """)
    Optional<SynthWaveUser> selectSynthWaveUserByUsername(String username);
}
