package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.role;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.RoleBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.role.embbedable.RoleBindingValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
interface RoleBindingJpaRepository extends JpaRepository<RoleBindingData, RoleBindingValue> {

    @Modifying
    @Query("""
           DELETE FROM RoleBindingData binding
           WHERE binding.value.roleId IN :roleIdCollection
           """)
    int deleteRoleById(@Param("roleIdCollection") Collection<Long> roleIdCollection);

    @Modifying
    @Query("""
           DELETE FROM RoleBindingData binding
           WHERE binding.value.userId=:userId
           AND binding.value.roleId IN :roleIdCollection
           """)
    int deleteRoleByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleIdCollection") Collection<Long> roleIdCollection);

}
