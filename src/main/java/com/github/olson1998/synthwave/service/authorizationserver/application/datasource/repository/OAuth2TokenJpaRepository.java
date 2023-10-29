package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.OAuth2TokenData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.OAuth2TokenDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OAuth2TokenJpaRepository extends JpaRepository<OAuth2TokenData, OAuth2TokenDesc> {

    @Query("SELECT token FROM OAuth2TokenData token WHERE token.description.authorizationId=:authorizationId")
    List<OAuth2TokenData> selectOAuth2TokensByAuthorizationId(String authorizationId);

    @Modifying
    @Query("DELETE FROM OAuth2TokenData token WHERE token.description.authorizationId=:authorizationId")
    int deleteTokenByAuthorizationId(String authorizationId);
}
