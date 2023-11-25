package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
interface RedirectJpaRepository extends JpaRepository<RedirectData, TSID> {

    @Query("""
           SELECT redirectURI
           FROM RedirectData redirectURI
           LEFT OUTER JOIN RedirectURIBindingData binding
           ON redirectURI.id=binding.properties.redirectURIId
           WHERE binding.properties.registeredClientId=:registeredClientId
           """)
    List<RedirectData> selectRedirectURIByRegisteredClientId(@Param("registeredClientId") TSID registeredClientId);

    @Query("""
           SELECT redirectURI
           FROM RedirectData redirectURI
           WHERE
           (redirectURI.uri IN :redirectURISet AND redirectURI.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope.POST_LOGIN)
           OR
           (redirectURI.uri IN :postLogoutRedirectURISet AND redirectURI.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope.POST_LOGOUT)
           """)
    List<RedirectData> selectRedirectURIIdInRedirectURISetAndPostLogoutRedirectURISet(@Param("redirectURISet") Set<String> redirectURISet,
                                                                                      @Param("postLogoutRedirectURISet") Set<String> postLogoutRedirectURISet);
}
