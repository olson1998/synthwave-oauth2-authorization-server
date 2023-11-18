package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIScope;
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
           SELECT redirectUri
           FROM RedirectURIData redirectUri
           LEFT OUTER JOIN RedirectURIBindingData redirectUriBinding
           ON redirectUri.id=redirectUriBinding.redirectURIId
           LEFT OUTER JOIN RegisteredClientData registeredClient
           ON redirectUriBinding.registeredClientId=registeredClient.id
           LEFT OUTER JOIN UserData user
           ON registeredClient.userId=user.id
           LEFT OUTER JOIN AffiliationData affiliation
           ON user.id=affiliation.userId
           WHERE
           redirectUriBinding.registeredClientId=:registeredClientId AND
           (
           (redirectUriBinding.type=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.COMPANY_PRIVATE AND redirectUriBinding.affiliationProperties.companyCode=affiliation.properties.companyCode) OR
           (redirectUriBinding.type=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.DIVISION_PRIVATE AND redirectUriBinding.affiliationProperties.division=affiliation.properties.division) OR
           (redirectUriBinding.type=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.CLIENT_PRIVATE AND redirectUriBinding.registeredClientId=registeredClient.id)
           )
           """)
    List<RedirectURIData> selectRedirectURIByRegisteredClientId(@Param("registeredClientId")TSID registeredClientId);

    @Query("""
           SELECT DISTINCT redirectURI
           FROM RedirectURIData redirectURI
           LEFT OUTER JOIN RedirectURIBindingData bind
           ON redirectURI.id = bind.redirectURIId
           WHERE (:registeredClientId IS NOT NULL AND bind.registeredClientId=:registeredClientId AND bind.type=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.CLIENT_PRIVATE)
           OR (:code IS NOT NULL AND bind.affiliationProperties.companyCode=:code AND bind.type=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.COMPANY_PRIVATE)
           OR (:divi IS NOT NULL AND bind.affiliationProperties.division=:divi AND bind.type=com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.RedirectURIBindType.DIVISION_PRIVATE)
           """)
    List<RedirectURIData> selectRedirectURIByClientIdCompanyCodeAndDivision(@Param("registeredClientId") TSID registeredClientId,
                                                                            @Param("code") String code,
                                                                            @Param("divi") String divi);

    @Query("""
           SELECT redirectURI
           FROM RedirectURIData redirectURI
           WHERE redirectURI.uri IN :uriSet
           AND redirectURI.scope=:scope
           """)
    List<RedirectURIData> selectRedirectURIWhereURIInURISetAndScope(@Param("uriSet") Set<String> uriSet,
                                                                    @Param("scope") RedirectURIScope scope);
}
