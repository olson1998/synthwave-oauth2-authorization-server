package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.RedirectUriBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.UriBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
interface RedirectUriBindingJpaRepository extends JpaRepository<RedirectUriBindingData, UriBindingValue> {

    @Modifying
    @Query("""
           DELETE FROM RedirectUriBindingData binding
           WHERE binding.properties.uriId IN :redirectUriIdCollection
           """)
    int deleteRedirectUriBindingByRedirectUriId(@Param("redirectUriIdCollection") Collection<Long> redirectUriIdCollection);

    @Modifying
    @Query("""
           DELETE FROM RedirectUriBindingData binding
           WHERE binding.properties.uriId IN :idCollection
           AND binding.properties.registeredClientId=:registeredClientId
           """)
    int deleteRedirectByIdAndRegisteredClientId(@Param("idCollection") Collection<Long> idCollection, @Param("registeredClientId") Long registeredClientId);

}
