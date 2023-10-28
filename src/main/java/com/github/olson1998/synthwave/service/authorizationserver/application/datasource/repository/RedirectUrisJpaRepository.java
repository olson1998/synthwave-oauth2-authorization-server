package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectUrisData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectUrisValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
interface RedirectUrisJpaRepository extends JpaRepository<RedirectUrisData, RedirectUrisValue> {

    @Query("SELECT uri FROM RedirectUrisData uri WHERE uri NOT IN :redirectUris")
    Set<RedirectUrisData> selectRedirectURIThatAreNotPresent(Collection<RedirectUrisValue> redirectUris);
}
