package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIData;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RedirectURIJpaRepository extends JpaRepository<RedirectURIData, TSID> {

    @Query("""
           SELECT DISTINCT redirectURI
           FROM RedirectURIData redirectURI
           LEFT OUTER JOIN RedirectURIBindData bind
           ON redirectURI.id = bind.uriId
           WHERE (:registeredClientId IS NOT NULL AND bind.registeredClientId=:registeredClientId AND bind.type='CLIENT_PRIVATE')
           OR (:code IS NOT NULL AND bind.affiliation.companyCode=:code AND bind.type='COMPANY_PRIVATE')
           OR (:divi IS NOT NULL AND bind.affiliation.division=:divi AND bind.type='DIVISION_PRIVATE')
           """)
    List<RedirectURIData> selectRedirectURIByClientIdCompanyCodeAndDivision(@Param("registeredClientId") TSID registeredClientId,
                                                                          @Param("code") String code,
                                                                          @Param("divi") String divi);
}
