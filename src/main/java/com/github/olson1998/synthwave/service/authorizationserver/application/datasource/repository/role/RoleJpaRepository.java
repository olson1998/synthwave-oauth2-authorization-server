package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.RoleData;
import org.joda.time.MutableDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RoleJpaRepository extends JpaRepository<RoleData, Long> {

    @Query("""
           SELECT role.name
           FROM RoleData role
           LEFT OUTER JOIN RoleBindingData binding
           ON role.id=binding.value.roleId
           WHERE role.expireOn > :now
           AND binding.value.userId=:userId
           """)
    String[] selectActiveRoleNamesByUserId(@Param("userId") Long userId,@Param("now") MutableDateTime now);

    @Query("""
           SELECT role.id
           FROM RoleData role
           WHERE role IN :roleDataExamples
           """)
    List<Long> selectRoleIdByExamples(@Param("roleDataExamples") List<Example<RoleData>> roleDataExamples);

}
