package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.AuthorizationGrantTypeBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.emb.AuthorizationGrantTypeBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorizationGrantTypeBindingJpaRepository extends JpaRepository<AuthorizationGrantTypeBindingData, AuthorizationGrantTypeBindingValue> {

    @Modifying
    @Query("""
           DELETE FROM AuthorizationGrantTypeBindingData binding
           WHERE binding.properties.authorizationGrantTypeId=:authorizationGrantTypeId
           """)
    int deleteAuthorizationGrantTypeBindingByAuthorizationGrantTypeId(@Param("authorizationGrantTypeId") Long authorizationGrantTypeId);

    @Modifying
    @Query("""
           DELETE FROM AuthorizationGrantTypeBindingData binding
           WHERE binding.properties.authorizationGrantTypeId=:authorizationGrantTypeId
           AND binding.properties.registeredClientId=:registeredClientId
           """)
    int deleteAuthorizationGrantTypeBindingByAuthorizationGrantTypeIdAndRegisteredClientId(@Param("registeredClientId") Long registeredClientId,
                                                                                           @Param("authorizationGrantTypeId") Long authorizationGrantTypeId);

}
