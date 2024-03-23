package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityData;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
interface AuthorityJpaRepository extends JpaRepository<AuthorityData, Long> {

    @Query("""
           SELECT authority.id
           FROM AuthorityData authority
           WHERE authority IN :authorityExamples
           """)
    List<Long> selectAuthorityIdByExamples(@Param("authorityExamples") List<Example<AuthorityData>> authorityExamples);

    @Query("""
           SELECT authority
           FROM AuthorityData authority
           WHERE authority.name IN :authoritiesNames
           """)
    List<AuthorityData> selectAuthorityByNames(@Param("authoritiesNames") Collection<String> authoritiesNames);

}
