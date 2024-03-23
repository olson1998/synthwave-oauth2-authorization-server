package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.user;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.user.UserPasswordData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface UserPasswordJpaRepository extends JpaRepository<UserPasswordData, Long> {

    @Modifying
    @Query("""
           UPDATE UserPasswordData password
           SET password.active=false
           WHERE password.active=true
           AND password.userId=:userId
           """)
    int updatePasswordActiveByUserId(@Param("userId") Long userId);
}
