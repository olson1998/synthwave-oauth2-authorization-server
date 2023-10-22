package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserPasswordData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserPasswordJpaRepository extends JpaRepository<UserPasswordData, TSID> {

    @Query("SELECT password.id FROM UserPasswordData password WHERE password.latestVersion=true AND password.userId=:userId")
    Optional<TSID> selectLatestPasswordIdByUserId(TSID userId);

    @Modifying
    @Query("UPDATE UserPasswordData password SET password.latestVersion=false WHERE password.id=:passwordId")
    int updatePasswordLatestVersionFalse(TSID passwordId);
}
