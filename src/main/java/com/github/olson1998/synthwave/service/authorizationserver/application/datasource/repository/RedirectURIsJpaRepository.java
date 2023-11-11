package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIsData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectUrisValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
interface RedirectURIsJpaRepository extends JpaRepository<RedirectURIsData, RedirectUrisValue> {

    @Query("SELECT uri FROM RedirectURIsData uri WHERE uri NOT IN :redirectUris")
    Set<RedirectURIsData> selectRedirectURIThatAreNotPresent(Collection<RedirectUrisValue> redirectUris);

    @Modifying
    @Query("DELETE FROM RedirectURIsData uri WHERE uri.redirect IN :redirectUris")
    int deleteRedirectURIByValue(@Param("redirectUris") Collection<RedirectUrisValue> redirectUris);
}
