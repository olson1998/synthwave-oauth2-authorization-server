package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.embbedable.AuthorityBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
interface AuthorityBindingJpaRepository extends JpaRepository<AuthorityBindingData, AuthorityBindingValue> {

    @Modifying
    @Query("""
           DELETE FROM AuthorityBindingData binding
           WHERE binding.value.authorityId IN :authoritiesIdCollection
           """)
    int deleteAuthorityBindingByAuthorityId(@Param("authoritiesIdCollection") Collection<Long> authoritiesIdCollection);

}
