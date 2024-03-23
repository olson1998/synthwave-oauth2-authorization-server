package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.ApplicationUserData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.query.ApplicationUserDetailsSearchQueryResultImpl;
import org.joda.time.MutableDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ApplicationUserJpaRepository extends JpaRepository<ApplicationUserData, Long> {

    @Query("""
           SELECT new com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.query.ApplicationUserDetailsSearchQueryResultImpl(
           )
           FROM ApplicationUserData user
           LEFT OUTER JOIN UserPasswordData password
           ON user.id=password.userId
           WHERE user.username=:username
           AND password.active=true
           """)
    Optional<ApplicationUserDetailsSearchQueryResultImpl> selectUserDetailsByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE ApplicationUserData user SET user.displayName=:displayName WHERE user.id=:id")
    int updateApplicationUserDisplayNameById(@Param("displayName") String displayName, @Param("id") long id);

    @Modifying
    @Query("UPDATE ApplicationUserData user SET user.expireOn=:expireOn WHERE user.id=:id")
    int updateApplicationUserExpireOnById(@Param("expireOn") MutableDateTime expireOn,@Param("id") long id);

    @Modifying
    @Query("UPDATE ApplicationUserData user SET user.displayName=:displayName, user.expireOn=:expireOn WHERE user.id=:id")
    int updateApplicationUserDisplayNameExpireOnById(@Param("displayName") String displayName,@Param("expireOn") MutableDateTime expireOn,@Param("id") long id);

    @Modifying
    @Query("DELETE FROM ApplicationUserData user WHERE user.id=:id")
    int deleteApplicationUserById(long id);
}
