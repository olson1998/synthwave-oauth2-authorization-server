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
           SELECT redirect
           FROM RedirectData redirect
           LEFT OUTER JOIN RedirectBoundData bound
           ON redirect.id=bound.properties.redirectId
           WHERE bound.properties.affiliationProperties.companyCode=:code
           AND bound.properties.affiliationProperties.division=:divi
           """)
    List<RedirectData> selectRedirectByCompanyCodeAndDivision(@Param("code") String companyCode, @Param("divi")String division);

    @Query("""
           SELECT redirect
           FROM RedirectData redirect
           WHERE
           (redirect.uri IN :redirects AND redirect.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectScope.POST_LOGIN)
           OR
           (redirect.uri IN :postLogoutRedirects AND redirect.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectScope.POST_LOGOUT)
           """)
    List<RedirectData> selectRedirectByRedirectAndPostLogoutURICompanyCodeAndDivision(@Param("redirects") Set<String> redirects,
                                                                                      @Param("postLogoutRedirects") Set<String> postLogoutRedirects);

    @Query("""
           SELECT redirect
           FROM RedirectData redirect
           LEFT OUTER JOIN RedirectBoundData bound
           ON redirect.id=bound.properties.redirectId
           WHERE
           (redirect.uri IN :redirects AND redirect.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectScope.POST_LOGIN)
           OR
           (redirect.uri IN :postLogoutRedirects AND redirect.scope=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectScope.POST_LOGOUT)
           AND
           bound.properties.affiliationProperties.companyCode=:code
           AND
           bound.properties.affiliationProperties.division=:divi
           """)
    List<RedirectData> selectRedirectByRedirectAndPostLogoutURICompanyCodeAndDivision(@Param("redirects") Set<String> redirects,
                                                                                      @Param("postLogoutRedirects") Set<String> postLogoutRedirects,
                                                                                      @Param("code") String comapnyCode,
                                                                                      @Param("divi") String division);
}
