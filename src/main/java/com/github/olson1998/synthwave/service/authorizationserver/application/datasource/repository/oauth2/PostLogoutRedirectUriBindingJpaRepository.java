package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.PostLogoutRedirectUriBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.UriBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
interface PostLogoutRedirectUriBindingJpaRepository extends JpaRepository<PostLogoutRedirectUriBindingData, UriBindingValue> {

    @Modifying
    @Query("""
           DELETE FROM PostLogoutRedirectUriBindingData binding
           WHERE binding.properties.uriId IN :idCollection
           AND binding.properties.registeredClientId=:registeredClientId
           """)
    int deletePostLogoutRedirectUriByIdAndRegisteredClientId(@Param("idCollection")Collection<Long> idCollection, @Param("registeredClientId") Long registeredClientId);

}
