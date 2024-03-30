package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
interface RedirectUriJpaRepository extends JpaRepository<RedirectUriData, Long> {

    @Query(
    """
    SELECT redirectUri.value
    FROM RedirectUriData redirectUri
    LEFT OUTER JOIN RedirectUriBindingData binding
    ON redirectUri.id=binding.properties.uriId
    WHERE binding.properties.registeredClientId=:registeredClientId
    AND redirectUri.expireOn > :timestamp
    """
    )
    Set<String> selectRedirectUriByRegisteredClientId(@Param("registeredClientId") Long registeredClientId,
                                                      @Param("timestamp")MutableDateTime timestamp);

    @Query("SELECT uri FROM RedirectUriData uri WHERE uri IN :redirectUriExamples")
    List<RedirectUriData> selectRedirectUriByExamples(@Param("redirectUriExamples") Collection<Example<RedirectUriData>> redirectUriExamples);

}
