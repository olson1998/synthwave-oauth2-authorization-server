package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData;
import org.joda.time.MutableDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface RedirectUriJpaRepository extends JpaRepository<RedirectUriData, Long> {

    @Query(
    """
    SELECT redirectUri.value
    FROM RedirectUriData redirectUri
    LEFT OUTER JOIN RedirectUriBindingData binding
    ON redirectUri.id=binding.binding.uriId
    WHERE binding.binding.registeredClientId=:registeredClientId
    AND redirectUri.expireOn > :timestamp
    """
    )
    Set<String> selectRedirectUriByRegisteredClientId(@Param("registeredClientId") Long registeredClientId,
                                                        @Param("timestamp")MutableDateTime timestamp);
}
