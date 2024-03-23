package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.authority;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.authority.AuthorityData;
import org.joda.time.MutableDateTime;
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
           SELECT authority
           FROM AuthorityData authority
           LEFT OUTER JOIN AuthorityBindingData binding
           ON authority.id=binding.value.authorityId
           WHERE authority.activeFrom > :now
           AND authority.expireOn < :now
           AND binding.value.userId=:userId
           """)
    String[] selectActiveAuthoritiesNamesByUserId(@Param("userId") Long userId, @Param("now") MutableDateTime now);

    @Query("""
           SELECT authority
           FROM AuthorityData authority
           LEFT OUTER JOIN AuthorityBindingData binding
           ON authority.id=binding.value.authorityId
           WHERE binding.value.userId=:userId
           """)
    List<AuthorityData> selectAuthorityByUserId(@Param("userId") Long userId);

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
