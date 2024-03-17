package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface RedirectUriJpaRepository extends JpaRepository<RedirectUriData, Long> {

    @Query(
    """
    SELECT redirectUri.model
    FROM RedirectUriData redirectUri
    LEFT OUTER JOIN RedirectUriBindingData binding
    ON redirectUri.id=binding.binding.uriId
    WHERE binding.binding.registeredClientId=:registeredClientId
    """
    )
    Set<URIModel> selectRedirectUriModelByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);
}
