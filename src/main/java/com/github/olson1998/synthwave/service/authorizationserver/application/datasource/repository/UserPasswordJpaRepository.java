package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.UserPasswordData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface UserPasswordJpaRepository extends JpaRepository<UserPasswordData, TSID> {

    @Modifying
    @Query("UPDATE UserPasswordData password SET password.latestVersion=false WHERE password.id=:passwordId")
    int updatePasswordLatestVersionFalse(TSID passwordId);
}
