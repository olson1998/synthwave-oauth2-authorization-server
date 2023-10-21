package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.AuthorityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface AuthorityJpaRepository extends JpaRepository<AuthorityData, String> {

    @Query("""
    WITH ChildAuthorities AS(
        SELECT parent.value authority
        FROM AuthorityData parent
        WHERE parent.value=:parentAuthority
        UNION
        SELECT child.value authority
        FROM ChildAuthorities childAuthorities
        JOIN AuthoritiesBindingData binding ON binding.parentAuthority=childAuthorities.value
        JOIN AuthorityData child ON binding.childAuthority=child.value
    )
    SELECT new org.springframework.security.core.authority.SimpleGrantedAuthority(authority) FROM ChildAuthorities
    """)
    Set<SimpleGrantedAuthority> selectSimpleGrantedAuthoritiesByParentAuthority(String parentAuthority);
}
