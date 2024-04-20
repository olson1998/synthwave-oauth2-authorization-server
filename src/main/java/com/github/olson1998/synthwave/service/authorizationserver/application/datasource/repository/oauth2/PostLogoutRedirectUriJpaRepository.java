package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.PostLogoutRedirectUriData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriData;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
interface PostLogoutRedirectUriJpaRepository extends JpaRepository<PostLogoutRedirectUriData, Long>, JpaSpecificationExecutor<PostLogoutRedirectUriData> {

    @Query(
    """
    SELECT postLogoutRedirectUri.value
    FROM PostLogoutRedirectUriData postLogoutRedirectUri
    LEFT OUTER JOIN PostLogoutRedirectUriBindingData binding
    ON postLogoutRedirectUri.id=binding.properties.postLogoutUriId
    WHERE binding.properties.registeredClientId=:registeredClientId
    """
    )
    List<String> selectPostLogoutRedirectUriByRegisteredClientId(@Param("registeredClientId") Long registeredClientId);

}
