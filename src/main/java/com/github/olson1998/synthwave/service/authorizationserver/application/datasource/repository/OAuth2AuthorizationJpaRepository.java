package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.OAuth2AuthorizationData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.OAuth2TokenDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface OAuth2AuthorizationJpaRepository extends JpaRepository<OAuth2AuthorizationData, String> {

    @Query("SELECT authorization FROM OAuth2AuthorizationData authorization WHERE authorization.id=:id")
    Optional<OAuth2AuthorizationData> selectAuthorizationById(String id);

    @Query("""
    SELECT authorization
    FROM OAuth2AuthorizationData authorization
    LEFT OUTER JOIN OAuth2TokenData token
    ON authorization.id=token.description.authorizationId
    WHERE token.value=:token
    AND token.description.tokenClass=:tokenClass
    """)
    Optional<OAuth2AuthorizationData> selectAuthorizationByTokenAndTokenClass(String token, Class<?> tokenClass);

    @Modifying
    @Query("DELETE FROM OAuth2AuthorizationData authorization WHERE authorization=:authorization")
    int deleteAuthorizationByAuthorizationData(OAuth2AuthorizationData authorization);
}
