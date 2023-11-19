package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
interface RedirectURIJpaRepository extends JpaRepository<RedirectURIData, TSID> {

    @Query("""
           SELECT redirectURI
           FROM RedirectURIData redirectURI
           LEFT OUTER JOIN RedirectURIBindingData binding
           ON redirectURI.id=binding.properties.redirectURIId
           WHERE binding.properties.registeredClientId=:registeredClientId
           """)
    List<RedirectURIData> selectRedirectURIByRegisteredClientId(@Param("registeredClientId") TSID registeredClientId);

    @Query("""
           SELECT redirectURI.id
           FROM RedirectURIData redirectURI
           WHERE
           (redirectURI.uri IN :redirectURISet AND redirectURI.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope.POST_LOGIN)
           OR
           (redirectURI.uri IN :postLogoutRedirectURISet AND redirectURI.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope.POST_LOGOUT)
           """)
    List<TSID> selectRedirectURIIdInRedirectURISetAndPostLogoutRedirectURISet(@Param("redirectURISet") Set<String> redirectURISet,
                                                                              @Param("postLogoutRedirectURISet") Set<String> postLogoutRedirectURISet);
}
