package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AuthorizationGrantTypeBindData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.AuthorizationGrantTypeBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
interface AuthorizationGrantTypeBindJpaRepository extends JpaRepository<AuthorizationGrantTypeBindData, AuthorizationGrantTypeBinding> {

    @Query("""
           SELECT DISTINCT authorizationGrantType.binding.authorizationGrantType
           FROM AuthorizationGrantTypeBindData authorizationGrantType
           WHERE authorizationGrantType.binding.registeredClientId=:registeredClientId
           """)
    Set<AuthorizationGrantType> selectAuthorizationGrantTypeByRegisteredClientId(@Param("registeredClientId")TSID registeredClientId);
}
